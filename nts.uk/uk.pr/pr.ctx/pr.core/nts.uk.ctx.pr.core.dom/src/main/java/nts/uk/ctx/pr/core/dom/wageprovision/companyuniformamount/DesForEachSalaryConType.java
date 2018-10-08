package nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.TargetClassBySalaryConType;

/**
* 給与契約形態ごとに指定する
*/
@AllArgsConstructor
@Getter
public class DesForEachSalaryConType extends DomainObject
{
    
    /**
    * 日給月給者
    */
    private TargetClassBySalaryConType monthSalaryPerDay;
    
    /**
    * 日給者
    */
    private TargetClassBySalaryConType aDayPayee;
    
    /**
    * 時給者
    */
    private TargetClassBySalaryConType hourlyPay;
    
    /**
    * 月給者
    */
    private TargetClassBySalaryConType monthlySalary;
    
    public DesForEachSalaryConType(Integer monthSalaryPerDay, Integer aDayPayee, Integer hourlyPay, Integer monthSalary) {
        this.monthlySalary = EnumAdaptor.valueOf(monthSalaryPerDay, TargetClassBySalaryConType.class);
        this.monthSalaryPerDay = EnumAdaptor.valueOf(aDayPayee, TargetClassBySalaryConType.class);
        this.aDayPayee = EnumAdaptor.valueOf(hourlyPay, TargetClassBySalaryConType.class);
        this.hourlyPay = EnumAdaptor.valueOf(monthSalary, TargetClassBySalaryConType.class);
    }
    
}
