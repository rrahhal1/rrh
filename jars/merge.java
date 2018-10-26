package rahhal27;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import com.google.gson.Gson;

public class merge {

	redishandler rh = new redishandler();

	public String getFullNam(String json) {
		
		json = rh.getfromredis("queue1");
		Person person = new Gson().fromJson(json, Person.class);
		String fn = person.getFullName();
		rh.saveToRedis("queue2",fn);
		return fn;
	}

	public String readTheStream() {
		SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("merge");

		JavaSparkContext ctx = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate(sparkConf));
		JavaStreamingContext context = new JavaStreamingContext(ctx, Durations.seconds(1));
		JavaReceiverInputDStream<String> lines = context.socketTextStream("localhost", 6379);
		lines.print();
		System.out.println(lines);
		//context.start();
		return getFullNam(lines.toString());
	}

	public static void main(String[] args) {
		merge mrg = new merge();
		mrg.readTheStream();

	}
}
