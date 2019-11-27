package nts.uk.ctx.pr.core.infra.entity.laborinsurance.laborinsuranceoffice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 公共職業安定所
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PUB_EMP_SEC_OFF")
public class QpbmtPubEmpSecOff extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtPubEmpSecOffPk qpbmtPubEmpSecOffPk;

    /**
     * 名称
     */
    @Basic(optional = false)
    @Column(name = "PUB_EMP_SEC_OFF_NAME\n")
    public String pubEmpSecOffName;

    @Override
    protected Object getKey() {
        return qpbmtPubEmpSecOffPk;
    }

    public PublicEmploymentSecurityOffice toDomain() {
        return new PublicEmploymentSecurityOffice(this.qpbmtPubEmpSecOffPk.cid, this.qpbmtPubEmpSecOffPk.pubEmpSecOffCode, this.pubEmpSecOffName);
    }

    public static QpbmtPubEmpSecOff toEntity(PublicEmploymentSecurityOffice domain) {
        return new QpbmtPubEmpSecOff(new QpbmtPubEmpSecOffPk(domain.getCompanyId(), domain.getPublicEmploymentSecurityOfficeCode().v()), domain.getPublicEmploymentSecurityOfficeName().v());
    }
}
