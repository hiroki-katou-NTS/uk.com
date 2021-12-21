/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit.Require;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;

/**
 * 　UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休暇.年次有給休暇.時間年休管理.時間年休管理.時間年休管理設定
 * The Class TimeAnnualSetting.
 */
/** 時間年休管理設定 **/
@Getter
public class TimeAnnualSetting extends DomainObject implements Serializable {

    /**
     * 時間年休管理設定
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

    /** The max day. */
    // 上限日数:時間年休の上限日数
    private TimeAnnualMaxDay maxYearDayLeave;

    /** 端数処理区分 */
    // 端数処理区分
    private TimeAnnualRoundProcesCla roundProcessClassific;

    // 時間年休一日の時間
    private TimeAnnualLeaveTimeDay timeAnnualLeaveTimeDay;
    
    // 時間年休の消化単位
    private TimeVacationDigestUnit timeVacationDigestUnit;
    
    /**
     * Instantiates a new time vacation setting.
     *
     * @param memento the memento
     */
    public TimeAnnualSetting(TimeAnnualSettingGetMemento memento) {
        this.maxYearDayLeave = memento.getMaxYearDayLeave();
        this.roundProcessClassific = memento.GetRoundProcessClassific();
        this.timeAnnualLeaveTimeDay = memento.getTimeAnnualLeaveTimeDay();
        this.timeVacationDigestUnit = memento.getTimeVacationDigestUnit();
    }

    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(TimeAnnualSettingSetMemento memento) {
        memento.setMaxYearDayLeave(this.maxYearDayLeave);
        if(this.roundProcessClassific == null){
        memento.setRoundProcessClassific(EnumAdaptor.valueOf(0, TimeAnnualRoundProcesCla.class));
        }else{
        	  memento.setRoundProcessClassific(this.roundProcessClassific);	
        }
        memento.setTimeAnnualLeaveTimeDay(this.timeAnnualLeaveTimeDay);
        memento.setTimeVacationDigestUnit(this.timeVacationDigestUnit);
    }
    /**
     * [6] 時間年休上限日数を取得
     * @param fromGrantTableDays
     * @return
     */
    public Optional<LimitedTimeHdDays> getLimitedTimeHdDays(Optional<LimitedTimeHdDays> fromGrantTableDays){
    	return this.maxYearDayLeave.getLimitedTimeHdDays(fromGrantTableDays);
    }
    
    /**
     * [7] 消化単位をチェックする
     * @param time 休暇使用時間
     * @param manage 年休管理区分
     */
    public boolean checkDigestUnits(Require require, AttendanceTime time, ManageDistinct manage) {
    	return this.timeVacationDigestUnit.checkDigestUnit(require, time, manage);
    }
}
