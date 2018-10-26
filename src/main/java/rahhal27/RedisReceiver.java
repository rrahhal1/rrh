package rahhal27;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

public abstract class RedisReceiver extends Receiver<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String host;
	private int dbNumber;
	String queueName;
	protected redishandler jedisHelper;
	private static final Logger LOGGER = Logger.getLogger(RedisReceiver.class);

	public RedisReceiver(String host, Integer dbNumber, String queueName) {
		super(StorageLevel.MEMORY_ONLY());
		this.host = host;
		this.queueName = queueName;
		this.dbNumber = dbNumber == null ? 0 : dbNumber;
	}

	@Override
	public void onStart() {
		LOGGER.info("Redis consumer onStart()...");

		try {
			redishandler.getInstance().connectToJedis();

			new Thread(this::receive).start();
		} catch (Exception exc) {
			LOGGER.warn("Could not connect to Redis : " + exc.getMessage());
			restart("Could not connect to Redis.");
		}

	}

	@Override
	public void onStop() {
		LOGGER.info("Redis consumer onStop()...");

	}

	public void receive() {
		String streamedData = null;
		try {

			while (!isStopped() && (streamedData = getData()) != null) {
				LOGGER.info("Received data '" + streamedData + "'");
				store(streamedData);
			}
		} catch (Throwable t) {
			LOGGER.info("Exception in redis receiver. Restarting ", t);

			restart("Error receiving data - trying to connect again.", t);
		} finally {
			LOGGER.info("Closing connection to Redis.");
		}
	}

	protected abstract String getData();
}
