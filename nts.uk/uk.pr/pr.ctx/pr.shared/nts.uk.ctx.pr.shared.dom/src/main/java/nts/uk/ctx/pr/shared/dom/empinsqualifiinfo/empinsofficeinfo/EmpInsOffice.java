package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員雇用保険事業所情報
*/
@Getter
public class EmpInsOffice extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;
    
    /**
    * 労働保険事業所コード
    */
    private LaborInsuranceOfficeCode laborInsCd;
    
    public EmpInsOffice(String histId, String laborInsCd) {
        this.histId = histId;
        this.laborInsCd = new LaborInsuranceOfficeCode(laborInsCd);
    }
    
}
