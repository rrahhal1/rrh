package rahhal27;

import java.io.Serializable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class redishandler implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static redishandler single_instance = null;

	 static Jedis jedis;

	public redishandler() {

		jedis = connectToJedis().getResource();
	}

	public static redishandler getInstance() {
		//if (single_instance == null)
			single_instance = new redishandler();

		return single_instance;
	}

	public JedisPool connectToJedis() {
		JedisPool pool;
		pool = new JedisPool("localhost", 6379);
		System.out.println("Successfully Connected to redis");
		return pool;
	}


}
