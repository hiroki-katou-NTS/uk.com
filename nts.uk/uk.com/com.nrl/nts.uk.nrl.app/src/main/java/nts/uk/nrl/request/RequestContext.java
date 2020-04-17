package nts.uk.nrl.request;

import nts.uk.nrl.response.NRLResponse;

/**
 * Request context.
 * 
 * @author manhnd
 *
 * @param <T>
 */
public abstract class RequestContext<T> {
	
	/**
	 * Get entity.
	 * @return entity
	 */
	protected abstract T getEntity();
	
	/**
	 * Get response.
	 * @return response
	 */
	protected abstract NRLResponse getResponse();
}
