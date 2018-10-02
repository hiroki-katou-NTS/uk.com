package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 給与会社単価
*/
@Getter
public class PayrollUnitPrice extends AggregateRoot {
    
    /**
    * コード
    */
    private CompanyUnitPriceCode code;
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * 名称
    */
    private SalaryUnitPriceName name;
    
    public PayrollUnitPrice(String code, String cid, String name) {
        this.cId = cid;
        this.code = new CompanyUnitPriceCode(code);
        this.name = new SalaryUnitPriceName(name);
    }
    
}
