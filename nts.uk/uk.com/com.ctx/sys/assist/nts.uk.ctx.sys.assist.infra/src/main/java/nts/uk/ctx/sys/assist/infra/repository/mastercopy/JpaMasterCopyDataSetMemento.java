package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryNo;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyCategory;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyData;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspctMastercopyDataPK;

/**
 * The Class JpaMasterCopyDataSetMemento.
 */
public class JpaMasterCopyDataSetMemento implements MasterCopyDataSetMemento {
	private SspctMastercopyCategory categoryEntity;
	
	/** The data entities. */
	private List<SspctMastercopyData> dataEntities;

	/**
	 * Instantiates a new jpa master copy data set memento.
	 *
	 * @param dataEntites
	 *            the entity
	 */
	public JpaMasterCopyDataSetMemento(SspctMastercopyCategory categoryEntity,List<SspctMastercopyData> dataEntites) {
		this.categoryEntity = categoryEntity;
		this.dataEntities = dataEntites;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataSetMemento#setTargetTable(
	 * nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo)
	 */
	@Override
	public void setTargetTable(List<TargetTableInfo> targetTables) {
		int categoryNo = this.categoryEntity.getCategoryNo();
		this.dataEntities =  targetTables.stream().map(e -> {
			SspctMastercopyDataPK pk = new SspctMastercopyDataPK(categoryNo, e.getTableNo().v());
			return new SspctMastercopyData(pk,new BigDecimal(e.getCopyAttribute().value), e.getKey().getKEY1().v(), e.getKey().getKEY2().get().v(),
					e.getKey().getKEY3().get().v(), e.getKey().getKEY4().get().v(), e.getKey().getKEY5().get().v(), e.getTableName().v());
		}).collect(Collectors.toList());
	}

	@Override
	public void setCategoryNo(MasterCopyCategoryNo categoryNo) {
		//categoryEntity
		this.categoryEntity.setCategoryNo(categoryNo.v());
		//dataEntites
		this.dataEntities.stream().forEach(e -> {
			SspctMastercopyDataPK pk = e.getId();
			if(pk==null) {
				pk = new SspctMastercopyDataPK(categoryNo.v(), null);
			}
			e.setId(pk);
		});
	}

}
