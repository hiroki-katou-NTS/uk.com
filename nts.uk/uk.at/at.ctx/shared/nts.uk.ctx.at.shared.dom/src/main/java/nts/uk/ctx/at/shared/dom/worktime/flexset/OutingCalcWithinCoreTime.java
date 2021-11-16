package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author hieunm
 * コアタイム内の外出計算
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.フレックス勤務設定.コアタイム内の外出計算
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

    /**
     * コア内と外を分けて計算するかどうか判断
     * @param reason 外出理由
     * @return 分けるかどうか（true:分ける、false:分けない）
     */
	public boolean isSeparateCoreInOutCalc(GoingOutReason reason){
		switch (reason){
		case PUBLIC:		// 公用
		case COMPENSATION:	// 有償
			return false;
		default:
			return this.especialCalc.isUse();
		}
	}
}
