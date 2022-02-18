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
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;

/**
 * 　時間年休管理設定
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

	/** The time manage type. */
    // 時間年休管理区分
    private ManageDistinct timeManageType;

    /** The time unit. */
    // 時間年休消化単位
    private TimeDigestiveUnit timeUnit;

    /** The max day. */
    // 上限日数:時間年休の上限日数
    private TimeAnnualMaxDay maxYearDayLeave;

//    /** The is enough time one day. */
//    // 1日の時間未満の時間年休を積立年休とする
//    private boolean isEnoughTimeOneDay;

    /** 端数処理区分 */
    // 端数処理区分
    private TimeAnnualRoundProcesCla roundProcessClassific;

    // 時間年休一日の時間
    private TimeAnnualLeaveTimeDay timeAnnualLeaveTimeDay; 
    
    /**
     * Instantiates a new time vacation setting.
     *
     * @param memento the memento
     */
    public TimeAnnualSetting(TimeAnnualSettingGetMemento memento) {
        this.timeManageType = memento.getTimeManageType();
        this.timeUnit = memento.getTimeUnit();
        this.maxYearDayLeave = memento.getMaxYearDayLeave();
        this.roundProcessClassific = memento.GetRoundProcessClassific();
        this.timeAnnualLeaveTimeDay = memento.getTimeAnnualLeaveTimeDay();
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
        if(this.roundProcessClassific == null){
        memento.setRoundProcessClassific(EnumAdaptor.valueOf(0, TimeAnnualRoundProcesCla.class));
        }else{
        	  memento.setRoundProcessClassific(this.roundProcessClassific);	
        }
        memento.setTimeAnnualLeaveTimeDay(this.timeAnnualLeaveTimeDay);
    }
    
    /**
     * [C-0]
     */
    public TimeAnnualSetting(ManageDistinct timeManageType, TimeDigestiveUnit timeUnit,
			TimeAnnualMaxDay maxYearDayLeave, TimeAnnualRoundProcesCla roundProcessClassific,
			TimeAnnualLeaveTimeDay timeAnnualLeaveTimeDay) {
		super();
		this.timeManageType = timeManageType;
		this.timeUnit = timeUnit;
		this.maxYearDayLeave = maxYearDayLeave;
		this.roundProcessClassific = roundProcessClassific;
		this.timeAnnualLeaveTimeDay = timeAnnualLeaveTimeDay;
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
    public List<Integer> getDailyAttendItemsNotAvailable(ManageDistinct distinct){
    	if (!this.isManageTimeAnnualLeave(distinct)) {
    		return this.getDailyAttdItemsCorrespondAnnualLeave();
    	}
    	return new ArrayList<>();
    }
    
    /**
     * [4] 利用できない月次の勤怠項目を取得する
     */
    public List<Integer> getMonthlyAttendItemsNotAvailable(ManageDistinct distinct){
    	List<Integer> timeAnnualLeaveItems = new ArrayList<>();
    	if (!this.isManageTimeAnnualLeave(distinct)) {
    		// $時間年休項目
    		timeAnnualLeaveItems.addAll(this.getAttdItemsDoNotIncludeMaximumNumberDays());
    	}
    	
    	// $上限項目
    	List<Integer> upperlimitItems = maxYearDayLeave.getMonthAttendItemsNotAvailable(distinct, timeManageType);
    	timeAnnualLeaveItems.addAll(upperlimitItems);
    	
    	return timeAnnualLeaveItems;
    }
    
    /**
     * [5] 時間年休を管理するか
     */
    public boolean isManageTimeAnnualLeave(ManageDistinct distinct) {
    	return distinct == ManageDistinct.YES && timeManageType == ManageDistinct.YES;
    }
    
    /**
     * [prv-1] 上限日数を含まない時間年休に対応する月次の勤怠項目を取得する
     */
    private List<Integer> getAttdItemsDoNotIncludeMaximumNumberDays(){
		return Arrays.asList(1424,1425,1426,1429,1430,1431,1861,1862);
    }
    
    /**
     * [6] 時間年休上限日数を取得
     * @param fromGrantTableDays
     * @return
     */
    public Optional<LimitedTimeHdDays> getLimitedTimeHdDays(Optional<LimitedTimeHdDays> fromGrantTableDays){
    	return this.maxYearDayLeave.getLimitedTimeHdDays(fromGrantTableDays);
    }
}
