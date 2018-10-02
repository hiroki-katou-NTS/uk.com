package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.Memo;

/**
* 給与会社単価設定
*/
@Getter
public class PayrollUnitPriceSetting extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 金額
    */
    private SalaryUnitPrice amountOfMoney;
    
    /**
    * メモ
    */
    private Optional<Memo> notes;
    
    /**
    * 固定的賃金
    */
    private SettingFixedWages fixedWage;
    
    public PayrollUnitPriceSetting(String hisId, BigDecimal amountOfMoney, Integer targetClass, Integer monthSalaryPerDay, Integer aDayPayee, Integer hourlyPay, Integer monthSalary, int setClassification, String notes) {
        this.historyId = hisId;
        this.amountOfMoney = new SalaryUnitPrice(amountOfMoney);
        this.fixedWage = new SettingFixedWages(setClassification,targetClass,monthSalaryPerDay,aDayPayee,hourlyPay,monthSalary);
        this.notes = notes == null ? Optional.empty() : Optional.of(new Memo(notes));
    }
    
}
