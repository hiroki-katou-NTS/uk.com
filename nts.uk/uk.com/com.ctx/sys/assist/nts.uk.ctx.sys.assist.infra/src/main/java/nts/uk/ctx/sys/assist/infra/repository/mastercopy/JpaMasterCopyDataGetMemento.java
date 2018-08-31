package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyTarget;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;

/**
 * The Class JpaMasterCopyDataGetMemento.
 */
public class JpaMasterCopyDataGetMemento implements MasterCopyDataGetMemento {

	/** The entity. */
	private SspmtMastercopyData entity;

	/**
	 * Instantiates a new jpa master copy data get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaMasterCopyDataGetMemento(SspmtMastercopyData entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#
	 * getMasterCopyId()
	 */
	@Override
	public String getMasterCopyId() {
		return this.entity.getId().getMasterCopyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#
	 * getMasterCopyTarget()
	 */
	@Override
	public MasterCopyTarget getMasterCopyTarget() {
		return new MasterCopyTarget(this.entity.getId().getMasterCopyTarget().toString());
	}

}
