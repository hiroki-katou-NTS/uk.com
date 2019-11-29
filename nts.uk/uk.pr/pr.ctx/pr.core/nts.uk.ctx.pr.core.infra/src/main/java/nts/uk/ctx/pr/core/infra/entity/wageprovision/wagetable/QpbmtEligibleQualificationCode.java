package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 資格グループ設定.対象資格コード
 */

@NoArgsConstructor
@Entity
@Table(name = "QPBMT_ELIGIBLE_QUALIFY_CD")
public class QpbmtEligibleQualificationCode extends UkJpaEntity {

	@EmbeddedId
	public QpbmtEligibleQualificationCodePk pk;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "QUALIFY_GROUP_CD", referencedColumnName = "QUALIFY_GROUP_CD", insertable = false, updatable = false) })
	public QpbmtQualificationGroupSetting qualificationGroupSet;

	@Override
	protected Object getKey() {
		return pk;
	}

	public QpbmtEligibleQualificationCode(String companyId, String qualificationGroupCode,
			String eligibleQualificationCode) {
		this.pk = new QpbmtEligibleQualificationCodePk(companyId, qualificationGroupCode, eligibleQualificationCode);
	}
}
