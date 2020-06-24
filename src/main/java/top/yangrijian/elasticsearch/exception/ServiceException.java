package top.yangrijian.elasticsearch.exception;

/**
 * SLOGAN:码出高效！码出未来！
 *
 * @Description:
 * @author: 迷羊
 * @Date: 2020-06-24 18:00:17
 */
public class ServiceException extends RuntimeException {

	public ServiceException(String message, Exception e) {
		super(message, e);
	}
}
