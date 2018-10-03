package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.wageprovision.unitpricename.SetFixedWageClass;

/**
* 固定的賃金の設定
*/
@AllArgsConstructor
@Getter
public class SettingFixedWages extends DomainObject
{
    
    /**
    * 設定区分
    */
    private SetFixedWageClass setClassification;
    
    /**
    * 全員一律
    */
    private Optional<DesByAllMembers> flatAllEmployees;
    
    /**
    * 給与契約形態ごと
    */
    private Optional<DesForEachSalaryConType> perSalaryConType;
    
    public SettingFixedWages(int setClassification,Integer targetClass,Integer monthSalaryPerDay, Integer aDayPayee, Integer hourlyPay, Integer monthSalary) {
        this.setClassification = EnumAdaptor.valueOf(setClassification, SetFixedWageClass.class);
        this.flatAllEmployees = Optional.of(new DesByAllMembers(targetClass));
        this.perSalaryConType = Optional.of(new DesForEachSalaryConType(monthSalaryPerDay,aDayPayee,hourlyPay,monthSalary));
    }
    
}
