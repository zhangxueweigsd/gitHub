- 这两个key是固定的，一个是token，一个是时间戳。
local tokens_key = KEYS[1]      --上面的request_rate_limiter.{127.0.0.1}.tokens
local timestamp_key = KEYS[2]  --上面的request_rate_limiter.{127.0.0.1}.timestamp

local rate = tonumber(ARGV[1])          -- 令牌桶填充速率，对应配置中的redis-rate-limiter.replenishRate
local capacity = tonumber(ARGV[2])   -- 令牌桶总容量，对应配置中的redis-rate-limiter.burstCapacity
local now = tonumber(ARGV[3])          --当前时间，单位是秒
local requested = tonumber(ARGV[4]) --写死的，值是 1

local fill_time = capacity/rate      --满负荷下 消耗完令牌需要几秒
local ttl = math.floor(fill_time*2)  --超过这个等待时间，请求丢弃

-- 剩余可用tokens
local last_tokens = tonumber(redis.call("get", tokens_key))
if last_tokens == nil then
  last_tokens = capacity
end

--最后刷新时间，也就是最后一个请求执行时间
local last_refreshed = tonumber(redis.call("get", timestamp_key))
if last_refreshed == nil then
  last_refreshed = 0
end


local delta = math.max(0, now-last_refreshed)
-- 这个delta和filled_tokens 是关键，每隔一秒生成N个令牌存到令牌桶中就是通过这两个参数计算的。
-- filled_tokens ：取（令牌桶总容量， 总的可用token（目前剩余的token+最后一次请求到现在应该创建的token））的小值，即执行到这一步剩余的token数量
local filled_tokens = math.min(capacity, last_tokens+(delta*rate)) 
--这一步是关键，判断是否获取到令牌
local allowed = filled_tokens >= requested 
local new_tokens = filled_tokens
local allowed_num = 0
if allowed then 
  --如果获取到令牌，则redis缓存中的令牌就扣除相应数量
  new_tokens = filled_tokens - requested
  allowed_num = 1
end

redis.call("setex", tokens_key, ttl, new_tokens)   -- 设置新的令牌数量
redis.call("setex", timestamp_key, ttl, now)          --设置当前请求时间

-- 返回{可用数量， 新的剩余令牌数量}
return { allowed_num, new_tokens }