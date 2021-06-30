package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flexset.OutingCalcWithinCoreTimeGetMemento;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * the class Outing Calc Within Core Time Dto
 */
@Getter
@Setter
public class OutingCalcWithinCoreTimeDto implements OutingCalcWithinCoreTimeGetMemento {

    /** the remove from work time */
    private Integer removeFromWorkTime;

    /** the especial calculation */
    private Integer especialCalc;

    @Override
    public NotUseAtr getRemoveFromWorkTime() {
        return NotUseAtr.valueOf(this.removeFromWorkTime);
    }

    @Override
    public NotUseAtr getEspecialCalc() {
        return NotUseAtr.valueOf(this.especialCalc);
    }
}
