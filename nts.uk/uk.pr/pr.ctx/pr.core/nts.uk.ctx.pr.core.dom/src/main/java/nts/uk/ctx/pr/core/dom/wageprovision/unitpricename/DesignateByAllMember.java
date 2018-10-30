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
* 全員一律で指定する
*/
@AllArgsConstructor
@Getter
public class DesignateByAllMember extends DomainObject
{
    
    /**
    * 対象区分
    */
    private CategoryFixedWage targetClassification;
    
    public DesignateByAllMember(Integer targetClassification) {
        this.targetClassification = EnumAdaptor.valueOf(targetClassification, CategoryFixedWage.class);
    }
    
}
