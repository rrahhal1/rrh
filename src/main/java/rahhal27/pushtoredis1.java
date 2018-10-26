package rahhal27;

import java.util.Iterator;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;


public class pushtoredis1 implements VoidFunction<JavaRDD<String>> {


	private static final long serialVersionUID = 1L;

	@Override
	public void call(JavaRDD<String> arg0) throws Exception {
		arg0.foreachPartition(new VoidFunction<Iterator<String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Iterator<String> it) throws Exception {
				while (it.hasNext()) {

					String str = it.next();
					redishandler.jedis.rpush("queue2", str);
					System.out.println(str);

				}
			};

		});

	}
}
