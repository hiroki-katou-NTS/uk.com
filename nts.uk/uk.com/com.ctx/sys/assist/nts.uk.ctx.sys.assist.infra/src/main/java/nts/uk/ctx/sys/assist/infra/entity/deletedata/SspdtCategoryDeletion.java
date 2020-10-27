package nts.uk.ctx.sys.assist.infra.entity.deletedata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.deletedata.CategoryDeletion;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "SSPDT_CATEGORY_DELETION")
@NoArgsConstructor
@AllArgsConstructor
public class SspdtCategoryDeletion extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspdtCategoryDeletionPK sspdtCategoryDeletionPK;
	
	/** The period deletion. */
	/** 自動設定対象期間 */
	@Column(name = "PERIOD_DELETION")
	public GeneralDate periodDeletion;
	
	/**
	 * システム種類
	 */
	@Column(name = "SYSTEM_TYPE")
	public int systemType;
	
	@ManyToOne
	@JoinColumn(name = "DEL_ID", referencedColumnName = "DEL_ID", insertable = false, updatable = false)
	public SspdtManualSetDeletion manualSetDeletion;
	
	@Override
	protected Object getKey() {
		return sspdtCategoryDeletionPK;
	}

	public CategoryDeletion toDomain() {
		return CategoryDeletion.createFromJavatype(this.sspdtCategoryDeletionPK.delId, 
				this.sspdtCategoryDeletionPK.categoryId, this.periodDeletion, this.systemType);
	}

	public static SspdtCategoryDeletion toEntity(CategoryDeletion categoryDeletion) {
		return new SspdtCategoryDeletion(new SspdtCategoryDeletionPK(
				categoryDeletion.getDelId(), categoryDeletion.getCategoryId()),
				categoryDeletion.getPeriodDeletion(), categoryDeletion.getSystemType());
	}

	public SspdtCategoryDeletion(SspdtCategoryDeletionPK sspdtCategoryDeletionPK, GeneralDate periodDeletion,
			int systemType) {
		this.sspdtCategoryDeletionPK = sspdtCategoryDeletionPK;
		this.periodDeletion = periodDeletion;
		this.systemType = systemType;
	}
}