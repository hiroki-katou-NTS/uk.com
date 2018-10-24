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
* 給与契約形態ごとに指定する
*/
@AllArgsConstructor
@Getter
public class PerSalaryContractType extends DomainObject
{
    
    /**
    * 月給者
    */
    private SalaryContractTypeClass monthlySalary;
    
    /**
    * 日給月給者
    */
    private SalaryContractTypeClass monthlySalaryPerday;
    
    /**
    * 日給者
    */
    private SalaryContractTypeClass dayPayee;
    
    /**
    * 時給者
    */
    private SalaryContractTypeClass hourlyPay;
    
    public PerSalaryContractType(Integer monthlySalary, Integer monthlySalaryPerday, Integer dayPayee, Integer hourlyPay) {
        this.monthlySalary = EnumAdaptor.valueOf(monthlySalary, SalaryContractTypeClass.class);
        this.monthlySalaryPerday = EnumAdaptor.valueOf(monthlySalaryPerday, SalaryContractTypeClass.class);
        this.dayPayee = EnumAdaptor.valueOf(dayPayee, SalaryContractTypeClass.class);
        this.hourlyPay = EnumAdaptor.valueOf(hourlyPay, SalaryContractTypeClass.class);
    }
    
}
