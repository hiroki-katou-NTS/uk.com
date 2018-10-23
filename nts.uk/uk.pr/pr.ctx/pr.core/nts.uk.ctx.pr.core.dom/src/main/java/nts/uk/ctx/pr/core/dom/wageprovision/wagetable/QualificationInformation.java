package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 資格情報
 */
@AllArgsConstructor
@Getter
public class QualificationInformation {

    /**
     * 会社ID
     */
    private String companyID;

    /**
     * 資格コード
     */
    private QualificationCode qualificationCode;

    /**
     * 資格名称
     */
    private QualificationName qualificationName;
    
    public QualificationInformation (String companyID, String qualificationCode, String qualificationName) {
        this.companyID = companyID;
        this.qualificationName = new QualificationName(qualificationName);
        this.qualificationCode = new QualificationCode(qualificationCode);
    }
}

