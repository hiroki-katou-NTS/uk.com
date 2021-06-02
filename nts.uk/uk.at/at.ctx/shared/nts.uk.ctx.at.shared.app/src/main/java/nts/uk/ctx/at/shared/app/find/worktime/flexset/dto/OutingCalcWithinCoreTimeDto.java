package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flexset.OutingCalcWithinCoreTimeSetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * the class Outing Calc Within Core Time Dto
 */
@Getter
@Setter
public class OutingCalcWithinCoreTimeDto implements OutingCalcWithinCoreTimeSetMemento {

    /** the remove from work time */
    private Integer removeFromWorkTime;

    /** the especial calculation */
    private Integer especialCalc;

    @Override
    public void setRemoveFromWorkTime(NotUseAtr removeFromWorkTime) {
        this.removeFromWorkTime = removeFromWorkTime.value;
    }

    @Override
    public void setEspecialCalc(NotUseAtr especialCalc) {
        this.especialCalc = especialCalc.value;
    }
}
