package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;

import javax.persistence.EntityManager;

/**
 * The Interface CopyHandler.
 */
public abstract class DataCopyHandler {

	/** The entity manager. */
	protected EntityManager entityManager;

	/** The copy method. */
    protected CopyMethod copyMethod;

	/** The company Id. */
    protected String companyId;
	
	/**
	 * Do copy.
	 */
	public abstract void doCopy();
}
