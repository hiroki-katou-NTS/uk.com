package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationName;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.*;

/**
 * 資格情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_QUALIFICATION_INFO")
public class QpbmtQualificationInformation {

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtQualificationInformationPk qualificationInformationPk;

    /**
     * 資格名称
     */
    @Basic(optional = false)
    @Column(name = "QUALIFICATION_NAME")
    public String qualificationName;

    public QualificationInformation toDomain () {
        return new QualificationInformation(qualificationInformationPk.cid, qualificationInformationPk.qualificationCode, qualificationName);
    }

    public static QpbmtQualificationInformation toEntity(QualificationInformation domain) {
        return new QpbmtQualificationInformation(new QpbmtQualificationInformationPk(AppContexts.user().companyId(), domain.getQualificationCode().v()), domain.getQualificationName().v());
    }
}

