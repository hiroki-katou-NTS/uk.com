package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyTarget;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspdtMastercopyData;

public class JpaMasterCopyDataGetMemento implements MasterCopyDataGetMemento {

	/** The entity. */
	private SspdtMastercopyData entity;

	/**
	 * Instantiates a new jpa master copy data get memento.
	 */
	public JpaMasterCopyDataGetMemento() {

	}

	/**
	 * Instantiates a new jpa master copy data get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaMasterCopyDataGetMemento(SspdtMastercopyData entity) {
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
		return this.entity.getMasterCopyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataGetMemento#
	 * getMasterCopyTarget()
	 */
	@Override
	public MasterCopyTarget getMasterCopyTarget() {
		return (MasterCopyTarget) this.entity.getMasterCopyTarget();
	}

}
