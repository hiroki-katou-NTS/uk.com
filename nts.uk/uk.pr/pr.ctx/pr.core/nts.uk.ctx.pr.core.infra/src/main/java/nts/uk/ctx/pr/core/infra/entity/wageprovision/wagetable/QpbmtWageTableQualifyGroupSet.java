package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingContent;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 賃金テーブル内容.資格グループ設定
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TBL_GRP_SET")
public class QpbmtWageTableQualifyGroupSet extends UkJpaEntity {

	@EmbeddedId
	public QpbmtWageTableQualifyGroupSetPk pk;

	@Column(name = "QUALIFY_GROUP_NAME")
	public String qualificationGroupName;

	@Column(name = "PAY_METHOD")
	public int paymentMethod;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qualificationGroupSet", orphanRemoval = true)
	public List<QpbmtWageTableGroupEligibleCode> eligibleQualificationCodes;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static QualificationGroupSettingContent toDomain(QpbmtWageTableQualifyGroupSet entity) {
		return new QualificationGroupSettingContent(entity.pk.qualificationGroupCode, entity.qualificationGroupName,
				entity.paymentMethod, entity.eligibleQualificationCodes.stream()
						.map(i -> i.pk.eligibleQualificationCode).collect(Collectors.toList()));
	}

	public static QpbmtWageTableQualifyGroupSet fromDomain(QualificationGroupSettingContent domain, String companyId,
			String wageTableCode, String historyId) {
		return new QpbmtWageTableQualifyGroupSet(
				new QpbmtWageTableQualifyGroupSetPk(companyId, wageTableCode, historyId,
						domain.getQualificationGroupCode().v()),
				domain.getQualificationGroupName().v(), domain.getPaymentMethod().value,
				domain.getEligibleQualificationCodes().stream().map(i -> new QpbmtWageTableGroupEligibleCode(companyId,
						wageTableCode, historyId, domain.getQualificationGroupCode().v(), i.v()))
						.collect(Collectors.toList()));
	}

}
