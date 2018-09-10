package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryName;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryOrder;
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
	public MasterCopyCategoryNo getCategoryNo() {
		return new MasterCopyCategoryNo(this.entity.getCategoryNo());
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento#
	 * getMasterCopyCategory()
	 */
	@Override
	public MasterCopyCategoryName getCategoryName() {
		return new MasterCopyCategoryName(this.entity.getCategoryName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento#
	 * getOrder()
	 */
	@Override
	public MasterCopyCategoryOrder getOrder() {
		return new MasterCopyCategoryOrder(this.entity.getCategoryOrder().intValue());
	}

}
