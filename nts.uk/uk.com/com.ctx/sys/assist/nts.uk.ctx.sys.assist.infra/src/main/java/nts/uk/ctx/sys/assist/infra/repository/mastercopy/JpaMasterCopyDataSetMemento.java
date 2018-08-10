package nts.uk.ctx.sys.assist.infra.repository.mastercopy;


import java.math.BigDecimal;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyData;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyDataPK;

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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento#setTargetTable(nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo)
	 */
	@Override
	public void setTargetTable(TargetTableInfo targetTable) {
		SspmtMastercopyDataPK pk = this.entity.getId();
		if(pk==null) {
			pk = new SspmtMastercopyDataPK(null, targetTable.getTableNo().v());
		}
		//set table no
		this.entity.getId().setTableNo(targetTable.getTableNo().v());
		this.entity.setCopyAtr(new BigDecimal(targetTable.getCopyAttribute().value));
		this.entity.setKey1(targetTable.getKey().getKEY1().v());
		//set key info
		this.entity.setKey2(targetTable.getKey().getKEY2().get().v());
		this.entity.setKey3(targetTable.getKey().getKEY3().get().v());
		this.entity.setKey4(targetTable.getKey().getKEY4().get().v());
		this.entity.setKey5(targetTable.getKey().getKEY5().get().v());
		this.entity.setTableName(targetTable.getTableName().v());
	}

	@Override
	public void setCategoryNo(MasterCopyCategoryNo categoryNo) {
		SspmtMastercopyDataPK pk = this.entity.getId();
		if(pk==null) {
			pk = new SspmtMastercopyDataPK(categoryNo.v(), null);
		}
		this.entity.getId().setCategoryNo(categoryNo.v());
	}


}
