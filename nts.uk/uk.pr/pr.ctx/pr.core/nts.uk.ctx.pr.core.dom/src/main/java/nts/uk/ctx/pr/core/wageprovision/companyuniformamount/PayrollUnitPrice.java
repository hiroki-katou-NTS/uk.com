package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 給与会社単価
*/
@Getter
public class PayrollUnitPrice extends AggregateRoot {
    /**
     * 会社ID
     */
    private String cId;

    /**
    * コード
    */
    private CompanyUnitPriceCode code;

    /**
    * 名称
    */
    private SalaryUnitPriceName name;
    
    public PayrollUnitPrice(String cid, String code, String name) {
        this.cId = cid;
        this.code = new CompanyUnitPriceCode(code);
        this.name = new SalaryUnitPriceName(name);
    }
    
}
