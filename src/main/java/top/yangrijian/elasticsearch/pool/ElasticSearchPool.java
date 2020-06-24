package top.yangrijian.elasticsearch.pool;

import org.elasticsearch.client.RestHighLevelClient;
import top.yangrijian.elasticsearch.config.ElasticSearchPoolConfig;
import top.yangrijian.elasticsearch.factory.ElasticSearchClientFactory;

/**
 * SLOGAN:码出高效！码出未来！
 *
 * @Description:
 * @author: 迷羊
 * @Date: 2020-05-29 11:55:34
 */
public class ElasticSearchPool extends Pool<RestHighLevelClient> {

	private ElasticSearchPoolConfig config;

	public ElasticSearchPool(ElasticSearchPoolConfig config){
		super(config, new ElasticSearchClientFactory(config));
		this.config = config;
	}

	public ElasticSearchPoolConfig getConfig() {
		return config;
	}

	public void setConfig(ElasticSearchPoolConfig config) {
		this.config = config;
	}
}
