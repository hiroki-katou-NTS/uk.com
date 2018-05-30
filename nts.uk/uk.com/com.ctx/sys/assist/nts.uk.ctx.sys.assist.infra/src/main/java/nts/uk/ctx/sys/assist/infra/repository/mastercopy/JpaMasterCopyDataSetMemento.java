package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyTarget;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;

/**
 * The Class JpaMasterCopyDataSetMemento.
 */
public class JpaMasterCopyDataSetMemento implements MasterCopyDataSetMemento {

	/** The entity. */
	private SspmtMastercopyData entity;

	/**
	 * Instantiates a new jpa master copy data set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaMasterCopyDataSetMemento(SspmtMastercopyData entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento#
	 * setMasterCopyId(java.lang.String)
	 */
	@Override
	public void setMasterCopyId(String masterCopyId) {
		this.entity.setMasterCopyId(masterCopyId);

	}

	/**
	 * Sets the master copy target.
	 *
	 * @param masterCopyTarget
	 *            the new master copy target
	 */
	@Override
	public void setMasterCopyTarget(MasterCopyTarget masterCopyTarget) {
		this.entity.setMasterCopyTarget(masterCopyTarget.toString());

	}

}
