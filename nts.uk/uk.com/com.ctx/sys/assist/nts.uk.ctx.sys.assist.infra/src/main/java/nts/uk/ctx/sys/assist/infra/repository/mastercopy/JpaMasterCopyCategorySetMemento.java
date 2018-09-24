/*
 * 
 */
package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.math.BigDecimal;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryName;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryOrder;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.SystemType;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyCategory;

public class JpaMasterCopyCategorySetMemento implements MasterCopyCategorySetMemento {

	/** The entity. */
	private SspmtMastercopyCategory entity;

	/**
	 * Instantiates a new jpa master copy category set memento.
	 */
	public JpaMasterCopyCategorySetMemento() {

	}

	/**
	 * Instantiates a new jpa master copy category set memento.
	 *
	 * @param entity
	 * 
	 * 
	 *            the entity
	 */
	public JpaMasterCopyCategorySetMemento(SspmtMastercopyCategory entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento#
	 * setSystemType(nts.uk.ctx.sys.assist.dom.mastercopy.SystemType)
	 */
	@Override
	public void setSystemType(SystemType systemType) {
		this.entity.setSystemType(BigDecimal.valueOf(systemType.value));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento#
	 * setMasterCopyId(java.lang.String)
	 */
	@Override
	public void setCategoryNo(MasterCopyCategoryNo categoryNo) {
		this.entity.setCategoryNo(categoryNo.v());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento#
	 * setMasterCopyCategory(nts.uk.ctx.sys.assist.dom.mastercopy.
	 * MasterCopyCategoryName)
	 */
	@Override
	public void setMasterCopyCategory(MasterCopyCategoryName masterCopyCategory) {
		this.entity.setCategoryName(masterCopyCategory.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento#
	 * setOrder(java.lang.Integer)
	 */
	@Override
	public void setOrder(MasterCopyCategoryOrder order) {
		this.entity.setCategoryOrder(new BigDecimal(order.v()));
	}

}
