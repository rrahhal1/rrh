package rahhal27;

import java.io.Serializable;


public class RedisListReceiver extends RedisReceiver implements Serializable {

	private static final long serialVersionUID = 1L;

	public RedisListReceiver(String host, Integer dbNumber, String queueName) {
		super(host, dbNumber, queueName);
	}

	@Override
	protected String getData() {

		return redishandler.jedis.lpop(queueName);
	}
}
