package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 固定的賃金の設定
*/
@AllArgsConstructor
@Getter
public class FixedWageSetting extends DomainObject
{
    
    /**
    * 設定区分
    */
    private SettingClassification settingAtr;
    
    /**
    * 全員一律
    */
    private Optional<DesignateByAllMember> everyoneEqualSet;
    
    /**
    * 給与契約形態ごと
    */
    private Optional<PerSalaryContractType> perSalaryContractType;
    
    public FixedWageSetting(int settingAtr, Integer targetClassification, Integer monthlySalary, Integer monthlySalaryPerday, Integer dayPayee, Integer hourlyPay) {
        this.settingAtr = EnumAdaptor.valueOf(settingAtr, SettingClassification.class);
        this.everyoneEqualSet = settingAtr == 1 ? Optional.of(new DesignateByAllMember(targetClassification)) : Optional.empty();
        this.perSalaryContractType = settingAtr == 0 ? Optional.of(new PerSalaryContractType(monthlySalary, monthlySalaryPerday, dayPayee, hourlyPay)) : Optional.empty();
    }
    
}
