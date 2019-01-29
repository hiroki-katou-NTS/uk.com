package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 資格情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_QUALIFICATION_INFO")
public class QpbmtQualificationInformation extends UkJpaEntity {

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtQualificationInformationPk pk;

	/**
	 * 資格名称
	 */
	@Basic(optional = false)
	@Column(name = "QUALIFICATION_NAME")
	public String qualificationName;

	/**
	 * 統合コード
	 */
	@Basic(optional = true)
	@Column(name = "INTERGRATE_CD")
	public String integrationCode;

	public QualificationInformation toDomain() {
		return new QualificationInformation(pk.cid, pk.qualificationCode, qualificationName, integrationCode);
	}

	public static QpbmtQualificationInformation toEntity(QualificationInformation domain) {
		return new QpbmtQualificationInformation(
				new QpbmtQualificationInformationPk(AppContexts.user().companyId(), domain.getQualificationCode().v()),
				domain.getQualificationName().v(),
				domain.getIntegrationCode().isPresent() ? domain.getIntegrationCode().get().v() : null);
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
