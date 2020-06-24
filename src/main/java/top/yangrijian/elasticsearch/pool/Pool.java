package top.yangrijian.elasticsearch.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yangrijian.elasticsearch.exception.ServiceException;

/**
 * SLOGAN:码出高效！码出未来！
 *
 * @Description:
 * @author: 迷羊
 * @Date: 2020-05-29 11:52:34
 */
public class Pool<T> implements Cloneable {

	/**
	 * 连接池
	 */
	protected GenericObjectPool<T> internalPool;

	private static final Logger LOGGER = LoggerFactory.getLogger(Pool.class);

	public Pool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
		initPool(poolConfig, factory);
	}

	private void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {

		if (this.internalPool != null) {
			try {
				closeInternalPool();
			} catch (Exception e) {
				LOGGER.error("init pool error", e);
				throw new ServiceException("init pool error", e);
			}
		}

		this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
	}

	private void closeInternalPool() {
		try {
			internalPool.close();
		} catch (Exception e) {
			LOGGER.error("Could not destroy the pool", e);
			throw new ServiceException("Could not destroy the pool", e);
		}
	}

	/**
	 * 从对象池中获取一个对象
	 * @return
	 */
	public T getResource() {
		try {
			return internalPool.borrowObject();
		} catch (Exception e) {
			LOGGER.error("Could not get a resource from the pool", e);
			throw new ServiceException("Could not get a resource from the pool", e);
		}
	}

	/**
	 * 对象使用完之后，归还到对象池
	 * @param resource
	 */
	public void returnResource(final T resource) {
		if (resource != null) {
			returnResourceObject(resource);
		}
	}

	private void returnResourceObject(final T resource) {
		if (resource == null) {
			return;
		}
		try {
			internalPool.returnObject(resource);
		} catch (Exception e) {
			LOGGER.error("Could not return the resource to the pool", e);
			throw new ServiceException("Could not return the resource to the pool", e);
		}
	}

	/**
	 * 销毁对象
	 * @param resource
	 */
	public void returnBrokenResource(final T resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	private void returnBrokenResourceObject(T resource) {
		try {
			internalPool.invalidateObject(resource);
		} catch (Exception e) {
			LOGGER.error("Could not return the resource to the pool", e);
			throw new ServiceException("Could not return the resource to the pool", e);
		}
	}

	public void destroy() {
		closeInternalPool();
	}

	/**
	 * @return 正在使用的数量
	 */
	public int getNumActive() {
		if (poolInactive()) {
			return -1;
		}
		return this.internalPool.getNumActive();
	}

	/**
	 * @return 闲置的数量
	 */
	public int getNumIdle() {
		if (poolInactive()) {
			return -1;
		}
		return this.internalPool.getNumIdle();
	}

	public int getNumWaiters() {
		if (poolInactive()) {
			return -1;
		}
		return this.internalPool.getNumWaiters();
	}

	public long getMeanBorrowWaitTimeMillis() {
		if (poolInactive()) {
			return -1;
		}
		return this.internalPool.getMeanBorrowWaitTimeMillis();
	}

	public long getMaxBorrowWaitTimeMillis() {
		if (poolInactive()) {
			return -1;
		}
		return this.internalPool.getMaxBorrowWaitTimeMillis();
	}

	private boolean poolInactive() {
		return this.internalPool == null || this.internalPool.isClosed();
	}

	/**
	 * 添加多少个连接给连接池
	 * @param count
	 * @throws Exception
	 */
	public void addObjects(int count) {
		try {
			for (int i = 0; i < count; i++) {
				this.internalPool.addObject();
			}
		} catch (Exception e) {
			LOGGER.error("Error trying to add idle objects", e);
			throw new ServiceException("Error trying to add idle objects", e);
		}
	}

}
