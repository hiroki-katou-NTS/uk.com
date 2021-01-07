/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class YearVacationManageSetting.
 */
// 年休管理設定
@Getter
public class ManageAnnualSetting extends DomainObject {
    
    /** The half day manage. */
    // 半日年休管理
    private HalfDayManage halfDayManage;
    
    /** The work day calculate. */
    // 年休を出勤日数として加算する
    private boolean isWorkDayCalculate;
    
    /** The remaining number setting. */
    // 残数設定
    private RemainingNumberSetting remainingNumberSetting;
    
    
    //年間所定労働日数
    private YearLyOfNumberDays yearlyOfNumberDays;
    
    /**
     * Instantiates a new manage annual setting.
     *
     * @param memento the memento
     */
    public ManageAnnualSetting(ManageAnnualSettingGetMemento memento) {
        super();
        this.halfDayManage = memento.getHalfDayManage();
        this.isWorkDayCalculate = memento.getIsWorkDayCalculate();
        this.remainingNumberSetting = memento.getRemainingNumberSetting();
        this.yearlyOfNumberDays = memento.getYearLyOfDays();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ManageAnnualSettingSetMemento memento) {
        memento.setHalfDayManage(this.halfDayManage);
        memento.setWorkDayCalculate(this.isWorkDayCalculate);
        memento.setRemainingNumberSetting(this.remainingNumberSetting);
        memento.setYearLyOfDays(this.yearlyOfNumberDays);
    }
}
