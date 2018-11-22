package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 資格グループ設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_QUALIFI_GROUP_SET")
public class QpbmtQualificationGroupSetting extends UkJpaEntity {

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtQualificationGroupSettingPk pk;

	/**
	 * 支払方法
	 */
	@Basic(optional = false)
	@Column(name = "PAYMENT_METHOD")
	public int paymentMethod;

	/**
	 * 資格グループ名
	 */
	@Basic(optional = false)
	@Column(name = "QUALIFICATION_GROUP_NAME")
	public String qualificationGroupName;

	public static List<QpbmtQualificationGroupSetting> toEntity(QualificationGroupSetting domain) {
		if (domain.getEligibleQualificationCode().isEmpty())
			return Arrays.asList(new QpbmtQualificationGroupSetting(
					new QpbmtQualificationGroupSettingPk(AppContexts.user().companyId(),
							domain.getQualificationGroupCode().v(), ""),
					domain.getPaymentMethod().value, domain.getQualificationGroupName().v()));
		return domain.getEligibleQualificationCode().stream()
				.map(item -> new QpbmtQualificationGroupSetting(
						new QpbmtQualificationGroupSettingPk(AppContexts.user().companyId(),
								domain.getQualificationGroupCode().v(), item.v()),
						domain.getPaymentMethod().value, domain.getQualificationGroupName().v()))
				.collect(Collectors.toList());
	}

	public static List<QualificationGroupSetting> toDomain(List<QpbmtQualificationGroupSetting> entities) {
		return entities.stream()
				.map(item -> new QualificationGroupSetting(item.pk.cid,
						item.pk.qualificationGroupCode, item.paymentMethod,
						Collections.emptyList(), item.qualificationGroupName))
				.collect(Collectors.toList());
	}

	public static Optional<QualificationGroupSetting> toDomainForGroup(List<QpbmtQualificationGroupSetting> entities) {
		if (entities.isEmpty())
			return Optional.empty();
		List<String> eligibleQualificationCode = new ArrayList<>();
		if (!entities.get(0).pk.eligibleQualificationCode.isEmpty())
			eligibleQualificationCode = entities.stream()
					.map(item -> item.pk.eligibleQualificationCode)
					.collect(Collectors.toList());
		return Optional.of(new QualificationGroupSetting(entities.get(0).pk.cid,
				entities.get(0).pk.qualificationGroupCode, entities.get(0).paymentMethod,
				eligibleQualificationCode, entities.get(0).qualificationGroupName));
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
}
