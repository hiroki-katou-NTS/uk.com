package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * コアタイム内の外出計算
 */
@Getter
@NoArgsConstructor
public class OutingCalcWithinCoreTime extends WorkTimeDomainObject {

    /** The remove from work time. */
    // コアタイム内の外出を就業時間から控除する
    private NotUseAtr removeFromWorkTime;

    /** The calculate sharing. */
    // コアタイム内外の外出を分けて計算する
    private NotUseAtr especialCalc;

    /**
     * Instant new OutingCalcWithinCoreTime
     * @param memento the memeto
     */
    public OutingCalcWithinCoreTime(OutingCalcWithinCoreTimeGetMemento memento){
        this.removeFromWorkTime = memento.getRemoveFromWorkTime();
        this.especialCalc = memento.getEspecialCalc();
    }

    /**
     * Instant new OutingCalcWithinCoreTime
     * @param remove the remove from work time
     * @param especial the especial calculation
     */
    public OutingCalcWithinCoreTime(NotUseAtr remove, NotUseAtr especial){
        this.removeFromWorkTime = remove;
        this.especialCalc = especial;
    }

    /**
     * Save to memento
     * @param memento the memento
     */
    public void saveToMemento(OutingCalcWithinCoreTimeSetMemento memento){
        memento.setEspecialCalc(this.especialCalc);
        memento.setRemoveFromWorkTime(this.removeFromWorkTime);

    }
}
