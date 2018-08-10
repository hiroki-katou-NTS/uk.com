package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.List;

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
	public void setTargetTable(List<TargetTableInfo> targetTables) {
		// TODO Auto-generated method stub
		
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
