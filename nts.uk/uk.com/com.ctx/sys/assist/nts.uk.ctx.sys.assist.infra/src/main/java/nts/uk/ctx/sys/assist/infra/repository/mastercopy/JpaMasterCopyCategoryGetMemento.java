package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryName;
import nts.uk.ctx.sys.assist.dom.mastercopy.SystemType;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyCategory;

/**
 * The Class JpaMasterCopyCategoryGetMemento.
 */
public class JpaMasterCopyCategoryGetMemento implements MasterCopyCategoryGetMemento {

	/** The entity. */
	private SspmtMastercopyCategory entity;

	/**
	 * Instantiates a new jpa master copy category get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaMasterCopyCategoryGetMemento(SspmtMastercopyCategory entity) {
		this.entity = entity;
	}

	/**
	 * Instantiates a new jpa master copy category get memento.
	 */
	public JpaMasterCopyCategoryGetMemento() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento#
	 * getSystemType()
	 */
	@Override
	public SystemType getSystemType() {
		return SystemType.valueOf(this.entity.getSystemType().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento#
	 * getMasterCopyId()
	 */
	@Override
	public String getMasterCopyId() {
		return this.entity.getMasterCopyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento#
	 * getMasterCopyCategory()
	 */
	@Override
	public MasterCopyCategoryName getMasterCopyCategory() {
		return new MasterCopyCategoryName(this.entity.getMasterCopyCategory().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento#
	 * getOrder()
	 */
	@Override
	public Integer getOrder() {
		return this.entity.getCategoryOrder().intValue();
	}

}
