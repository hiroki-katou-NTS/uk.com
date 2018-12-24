package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static QualificationGroupSettingContent toDomain(List<QpbmtWageTableQualifyGroupSet> listEntity) {
		return new QualificationGroupSettingContent(listEntity.get(0).pk.qualificationGroupCode, listEntity.get(0).qualificationGroupName,
				listEntity.get(0).paymentMethod,
				listEntity.stream().map(i -> i.pk.elegibleQualificationCode).collect(Collectors.toList()));
	}

	public static List<QpbmtWageTableQualifyGroupSet> fromDomain(QualificationGroupSettingContent domain,
			String historyId) {
		if (domain.getEligibleQualificationCodes().isEmpty())
			return Arrays.asList(new QpbmtWageTableQualifyGroupSet(
					new QpbmtWageTableQualifyGroupSetPk(historyId, domain.getQualificationGroupCode().v(), null),
					domain.getQualificationGroupName().v(), domain.getPaymentMethod().value));
		return domain.getEligibleQualificationCodes().stream()
				.map(i -> new QpbmtWageTableQualifyGroupSet(
						new QpbmtWageTableQualifyGroupSetPk(historyId, domain.getQualificationGroupCode().v(), i.v()),
						domain.getQualificationGroupName().v(), domain.getPaymentMethod().value))
				.collect(Collectors.toList());
	}

}
