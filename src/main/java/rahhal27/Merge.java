package rahhal27;


import java.io.IOException;

import java.io.Serializable;

import java.net.UnknownHostException;


import org.apache.spark.SparkConf;

import org.apache.spark.streaming.Durations;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;


public class Merge implements Serializable {

	private static final long serialVersionUID = 1L;
	redishandler rh = redishandler.getInstance();
	static RedisListReceiver rReceiver = new RedisListReceiver("localhost", 0, "queue1");

	public static void readTheStream() throws UnknownHostException, IOException {
		SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Merge");

		@SuppressWarnings("resource")
		JavaStreamingContext context = new JavaStreamingContext(sparkConf, Durations.seconds(10));
		JavaReceiverInputDStream<String> customReceiverStream = context.receiverStream(rReceiver);

		JavaDStream<String> a = customReceiverStream.map(new getfn());

		a.foreachRDD(new pushtoredis1());

		context.start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		Merge.readTheStream();

	}
}
