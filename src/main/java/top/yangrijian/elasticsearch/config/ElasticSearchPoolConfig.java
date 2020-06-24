package top.yangrijian.elasticsearch.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.HashSet;
import java.util.Set;

/**
 * SLOGAN:码出高效！码出未来！
 *
 * @Description: ElasticSearch 连接池配置类
 * @author: 迷羊
 * @Date: 2020-05-29 13:57:19
 */
public class ElasticSearchPoolConfig extends GenericObjectPoolConfig<RestHighLevelClient> {

	private String clusterName;

	Set<String> nodes = new HashSet<String>();

	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public Set<String> getNodes() {
		return nodes;
	}

	public void setNodes(Set<String> nodes) {
		this.nodes = nodes;
	}
}
