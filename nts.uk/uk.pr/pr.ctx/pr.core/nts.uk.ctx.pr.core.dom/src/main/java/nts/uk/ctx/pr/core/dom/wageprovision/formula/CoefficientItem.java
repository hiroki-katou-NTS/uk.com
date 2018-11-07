package nts.uk.ctx.pr.core.dom.wageprovision.formula;

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
* 係数項目
*/
@AllArgsConstructor
@Getter
public class CoefficientItem extends DomainObject
{
    
    /**
    * 勤怠項目
    */
    private Optional<ItemNameCode> attendanceItem;
    
    /**
    * 係数区分（固定項目）
    */
    private Optional<CoefficientClassification> coefficientClassification;
    
    public CoefficientItem(String attendanceItem, Integer coefficientClassification) {
        this.attendanceItem = attendanceItem == null ? Optional.empty() : Optional.of(new ItemNameCode(attendanceItem));
        this.coefficientClassification = coefficientClassification == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(coefficientClassification, CoefficientClassification.class));
    }
    
}
