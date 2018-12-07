package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationName;

/**
 * 資格情報
 */
@NoArgsConstructor
@Data
public class QualificationInformationDto {

    /**
     * 会社ID
     */
    private String companyID;

    /**
     * 資格コード
     */
    private String qualificationCode;

    /**
     * 資格名称
     */
    private String qualificationName;

    public static QualificationInformationDto fromDomainToDto(QualificationInformation domain) {
        QualificationInformationDto dto = new QualificationInformationDto();
        dto.companyID = domain.getCompanyID();
        dto.qualificationCode = domain.getQualificationCode().v();
        dto.qualificationName = domain.getQualificationName().v();
        return dto;
    }
}

