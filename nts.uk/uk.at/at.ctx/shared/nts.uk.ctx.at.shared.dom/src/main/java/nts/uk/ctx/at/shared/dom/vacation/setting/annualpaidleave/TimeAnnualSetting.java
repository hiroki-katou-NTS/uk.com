/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 　時間年休管理設定
 * The Class TimeAnnualSetting.
 */
@Getter
public class TimeAnnualSetting extends DomainObject implements Serializable {

    /**
     * 時間年休管理設定
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The time manage type. */
    // 時間年休管理区分
    private ManageDistinct timeManageType;

    /** The time unit. */
    // 時間年休消化単位
    private TimeDigestiveUnit timeUnit;

    /** The max day. */
    // 上限日数:時間年休の上限日数
    private TimeAnnualMaxDay maxYearDayLeave;

    /** The is enough time one day. */
    // 1日の時間未満の時間年休を積立年休とする
    private boolean isEnoughTimeOneDay;
    
    /** 端数処理区分 */
    // 端数処理区分
    private TimeAnnualRoundProcesCla roundProcessClassific;
    
    /** 1日の時間 */
    // 1日の時間 
    private Optional<AnnualTimePerDay> annualTimePerDay;
    
    /**
     * Instantiates a new time vacation setting.
     *
     * @param memento the memento
     */
    public TimeAnnualSetting(TimeAnnualSettingGetMemento memento) {
        this.timeManageType = memento.getTimeManageType();
        this.timeUnit = memento.getTimeUnit();
        this.maxYearDayLeave = memento.getMaxYearDayLeave();
        this.isEnoughTimeOneDay = memento.isEnoughTimeOneDay();
        this.roundProcessClassific = memento.GetRoundProcessClassific();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(TimeAnnualSettingSetMemento memento) {
        memento.setTimeManageType(this.timeManageType);
        memento.setTimeUnit(this.timeUnit);
        memento.setMaxYearDayLeave(this.maxYearDayLeave);
        memento.setEnoughTimeOneDay(this.isEnoughTimeOneDay);
        memento.setRoundProcessClassific(this.roundProcessClassific);
    }
}
