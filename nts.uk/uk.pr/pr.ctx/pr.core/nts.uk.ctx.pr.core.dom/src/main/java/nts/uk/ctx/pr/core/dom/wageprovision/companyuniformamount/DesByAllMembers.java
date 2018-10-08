package nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.FixedWageClassification;

/**
* 全員一律で指定する
*/
@AllArgsConstructor
@Getter
public class DesByAllMembers extends DomainObject
{
    
    /**
    * 対象区分
    */
    private FixedWageClassification targetClass;
    
    public DesByAllMembers(Integer targetClass) {
        this.targetClass = EnumAdaptor.valueOf(targetClass, FixedWageClassification.class);
    }
    
}
