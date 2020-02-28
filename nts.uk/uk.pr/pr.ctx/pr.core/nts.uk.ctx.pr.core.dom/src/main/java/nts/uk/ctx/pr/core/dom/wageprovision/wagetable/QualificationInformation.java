package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.IntegrationItemCode;

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
    
    /**
     * 統合コード
     */
    private Optional<IntegrationItemCode> integrationCode;
    
    public QualificationInformation (String companyID, String qualificationCode, String qualificationName, String integrationCode) {
        this.companyID = companyID;
        this.qualificationName = new QualificationName(qualificationName);
        this.qualificationCode = new QualificationCode(qualificationCode);
        if (integrationCode != null && !integrationCode.isEmpty())
        	this.integrationCode = Optional.of(new IntegrationItemCode(integrationCode));
        else
        	this.integrationCode = Optional.empty();
    }
}

