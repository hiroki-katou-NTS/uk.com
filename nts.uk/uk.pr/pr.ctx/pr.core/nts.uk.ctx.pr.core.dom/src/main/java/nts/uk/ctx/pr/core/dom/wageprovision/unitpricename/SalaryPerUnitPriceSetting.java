package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 給与個人単価設定
*/
@Getter
public class SalaryPerUnitPriceSetting extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private PerUnitPriceCode code;
    
    /**
    * 単価種類
    */
    private PerUnitPriceType unitPriceType;
    
    /**
    * 固定的賃金
    */
    private FixedWageSetting fixedWage;
    
    public SalaryPerUnitPriceSetting(String cid, String code, int unitPriceType, int settingAtr, Integer targetClassification, Integer monthlySalary, Integer monthlySalaryPerday, Integer dayPayee, Integer hourlyPay) {
        this.cid = cid;
        this.code = new PerUnitPriceCode(code);
        this.fixedWage = new FixedWageSetting(settingAtr, targetClassification, monthlySalary, monthlySalaryPerday, dayPayee, hourlyPay);
        this.unitPriceType = EnumAdaptor.valueOf(unitPriceType, PerUnitPriceType.class);
    }
    
}
