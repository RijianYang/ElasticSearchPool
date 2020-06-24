package top.yangrijian.elasticsearch;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import top.yangrijian.elasticsearch.config.ElasticSearchPoolConfig;
import top.yangrijian.elasticsearch.pool.ElasticSearchPool;

import java.io.IOException;
import java.util.Collections;

/**
 * SLOGAN:码出高效！码出未来！
 *
 * @Description:
 * @author: 迷羊
 * @Date: 2020-06-24 19:42:59
 */
public class Application {

	public static void main(String[] args) {
		ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
		config.setNodes(Collections.singleton("172.16.8.177:9200"));
		// ... 配置一些连接池配置，如连接池大小等，没有则使用默认值
		ElasticSearchPool pool = null;
		RestHighLevelClient client = null;
		try {
			pool = new ElasticSearchPool(config);
			pool.addObjects(5);
			client = pool.getResource();

			GetRequest getRequest = new GetRequest("miyang_test", "employee", "1");
			GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null && pool != null) {
				pool.returnResource(client);
			}
		}
	}
}
