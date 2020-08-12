package nts.uk.ctx.at.function.app.nrl.request;

import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;

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
