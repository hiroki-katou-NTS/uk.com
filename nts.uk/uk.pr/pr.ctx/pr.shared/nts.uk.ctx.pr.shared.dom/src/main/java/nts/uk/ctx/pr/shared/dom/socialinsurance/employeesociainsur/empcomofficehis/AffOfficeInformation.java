package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 所属事業所情報
*/
@Getter
public class AffOfficeInformation extends AggregateRoot {
    
    /**
    * 履歴
    */
    private String historyId;
    
    /**
    * 社会保険事業所
    */
    private SocialInsuranceOfficeCode socialInsurOfficeCode;
    
    public AffOfficeInformation(String socialInsuranceOfficeCd, SocialInsuranceOfficeCode socialInsuranceOfficeCode) {
        this.historyId = socialInsuranceOfficeCd;
        this.socialInsurOfficeCode = socialInsuranceOfficeCode;
    }
    
}
