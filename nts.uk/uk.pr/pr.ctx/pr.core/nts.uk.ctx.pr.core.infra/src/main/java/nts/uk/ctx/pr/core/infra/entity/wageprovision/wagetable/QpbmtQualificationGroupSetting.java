package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;
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
	@Column(name = "QUALIFY_GROUP_NAME")
	public String qualificationGroupName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qualificationGroupSet", orphanRemoval = true)
	public List<QpbmtEligibleQualificationCode> eligibleQualificationCodes;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public QualificationGroupSetting toDomain() {
		return new QualificationGroupSetting(pk.cid, pk.qualificationGroupCode, paymentMethod,
				eligibleQualificationCodes.stream().map(i -> i.pk.eligibleQualificationCode)
						.collect(Collectors.toList()),
				qualificationGroupName);
	}

	public static QpbmtQualificationGroupSetting fromDomain(QualificationGroupSetting domain) {
		return new QpbmtQualificationGroupSetting(new QpbmtQualificationGroupSettingPk(domain.getCompanyID(),
				domain.getQualificationGroupCode().v()),
				domain.getPaymentMethod().value,
				domain.getQualificationGroupName().v(),
				domain.getEligibleQualificationCode().stream()
						.map(i -> new QpbmtEligibleQualificationCode(domain.getCompanyID(),
								domain.getQualificationGroupCode().v(), i.v()))
						.collect(Collectors.toList()));
	}

}
