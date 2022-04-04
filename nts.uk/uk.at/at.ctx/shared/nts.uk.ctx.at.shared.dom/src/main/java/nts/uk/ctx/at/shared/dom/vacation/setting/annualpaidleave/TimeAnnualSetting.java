/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.MonthVacationGrantDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
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
     * [C-0]
     */
    public TimeAnnualSetting(TimeAnnualMaxDay maxYearDayLeave, TimeAnnualRoundProcesCla roundProcessClassific,
			TimeAnnualLeaveTimeDay timeAnnualLeaveTimeDay, TimeVacationDigestUnit timeVacationDigestUnit) {
		super();
		this.maxYearDayLeave = maxYearDayLeave;
		this.roundProcessClassific = roundProcessClassific;
		this.timeAnnualLeaveTimeDay = timeAnnualLeaveTimeDay;
		this.timeVacationDigestUnit = timeVacationDigestUnit;
	}
    
    /**
     * [1] 時間年休に対応する日次の勤怠項目を取得する
     */
    public List<Integer> getDailyAttdItemsCorrespondAnnualLeave(){
    	List<Integer> attendanceItemIds = Arrays.asList(502,514,595,601,607,613);
		return attendanceItemIds;
    }
    
    /**
     * [2] 時間年休に対応する月次の勤怠項目を取得する
     */
    public List<Integer> acquireMonthAttdItemsHourlyAnnualLeave(){
    	// $時間年休項目
    	List<Integer> annualLeaveItems = new ArrayList<>();
    	annualLeaveItems.addAll(this.getAttdItemsDoNotIncludeMaximumNumberDays());
    	// $上限項目
    	annualLeaveItems.addAll(maxYearDayLeave.acquiremonthAttendItemMaximumNumberDaysAnnualLeave());
    	return annualLeaveItems;
    }
    
    /**
     * [3] 利用できない日次の勤怠項目を取得する
     */
    public List<Integer> getDailyAttendItemsNotAvailable(TimeVacationDigestUnit.Require require, ManageDistinct distinct){
    	if (!this.isManageTimeAnnualLeave(require, distinct)) {
    		return this.getDailyAttdItemsCorrespondAnnualLeave();
    	}
    	return new ArrayList<>();
    }
    
    /**
     * [4] 利用できない月次の勤怠項目を取得する
     */
    public List<Integer> getMonthlyAttendItemsNotAvailable(TimeVacationDigestUnit.Require require, ManageDistinct distinct) {
    	List<Integer> timeAnnualLeaveItems = new ArrayList<>();
    	if (!this.isManageTimeAnnualLeave(require, distinct)) {
    		// $時間年休項目
    		timeAnnualLeaveItems.addAll(this.getAttdItemsDoNotIncludeMaximumNumberDays());
    	}
    	
    	ManageDistinct timeManageType = this.isManageTimeAnnualLeave(require, distinct) ? ManageDistinct.YES : ManageDistinct.NO;
    	// $上限項目
    	List<Integer> upperlimitItems = maxYearDayLeave.getMonthAttendItemsNotAvailable(distinct, timeManageType);
    	timeAnnualLeaveItems.addAll(upperlimitItems);
    	
    	return timeAnnualLeaveItems;
    }
    
    /**
     * [5] 時間年休を管理するか
     */
    public boolean isManageTimeAnnualLeave(TimeVacationDigestUnit.Require require, ManageDistinct distinct) {
    	return this.timeVacationDigestUnit.isVacationTimeManage(require, distinct);
    }
    
    /**
     * [6] 時間年休上限日数を取得
     * @param fromGrantTableDays
     * @return
     */
    public Optional<LimitedTimeHdDays> getLimitedTimeHdDays(Optional<LimitedTimeHdDays> fromGrantTableDays){
    	return this.maxYearDayLeave.getLimitedTimeHdDays(fromGrantTableDays);
    }
    
    //時間年休管理
    public boolean isManaged() {
    	return this.getTimeVacationDigestUnit().getManage() == ManageDistinct.YES;
    }
    
	/**
	 * 積立年休の付与数を取得する
	 */
    protected Optional<MonthVacationGrantDay> getAnnualLeavGrant(ManageDistinct yearManageType, int timeRemain, int timeAnnualLeavOneDay) {
		if (!yearManageType.isManaged() || !isManaged()) {
			return Optional.empty();
		}
		if(timeAnnualLeavOneDay == 0) {
			return Optional.empty();
		}
		if (this.getRoundProcessClassific() == TimeAnnualRoundProcesCla.TruncateOnDay0) {
			return Optional.of(MonthVacationGrantDay.createWithTruncate(Double.valueOf(timeRemain) / timeAnnualLeavOneDay));
		} else {
			return Optional.of(MonthVacationGrantDay.createWithRoundUp(Double.valueOf(timeRemain) / timeAnnualLeavOneDay));
		}
	}
	
    /**
     * [7] 消化単位をチェックする
     * @param time 休暇使用時間
     * @param manage 年休管理区分
     */
    public boolean checkDigestUnits(TimeVacationDigestUnit.Require require, AttendanceTime time, ManageDistinct manage) {
    	return this.timeVacationDigestUnit.checkDigestUnit(require, time, manage);
    }
    
    /**
     * [prv-1] 上限日数を含まない時間年休に対応する月次の勤怠項目を取得する
     */
    private List<Integer> getAttdItemsDoNotIncludeMaximumNumberDays(){
		return Arrays.asList(1424,1425,1426,1429,1430,1431,1861,1862);
    }
    
}
