package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;
/**
 * 資格グループ設定.対象資格コード
 */

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TBL_GRP_EQ_CD")
public class QpbmtWageTableGroupEligibleCode extends UkJpaEntity {

	@EmbeddedId
	public QpbmtWageTableGroupEligibleCodePk pk;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "WAGE_TABLE_CD", referencedColumnName = "WAGE_TABLE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "QUALIFY_GROUP_CD", referencedColumnName = "QUALIFY_GROUP_CD", insertable = false, updatable = false) })
	public QpbmtWageTableQualifyGroupSet qualificationGroupSet;

	@Override
	protected Object getKey() {
		return pk;
	}

	public QpbmtWageTableGroupEligibleCode(String companyId, String wageTableCode, String historyId,
			String qualificationGroupCode, String eligibleQualificationCode) {
		this.pk = new QpbmtWageTableGroupEligibleCodePk(companyId, wageTableCode, historyId, qualificationGroupCode,
				eligibleQualificationCode);
	}

}
