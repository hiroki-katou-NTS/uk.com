/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;

/**
 * The Class YearVacationManageSetting.
 */
// 年休管理設定
@Getter
public class ManageAnnualSetting extends DomainObject implements Serializable{

    /**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

//	/** The maximum day vacation. */
//    // 付与上限日数
//    private AnnualLeaveGrantDay maxGrantDay;

    /** The half day manage. */
    // 半日年休管理
    private HalfDayManage halfDayManage;

    /** The remaining number setting. */
    // 残数設定
    private RemainingNumberSetting remainingNumberSetting;

    //年間所定労働日数
    private YearLyOfNumberDays yearlyOfNumberDays;

//    /** The display setting. */
//    // 表示設定
//    private DisplaySetting displaySetting;

    /**
     * 	[C-0]
     */
    public ManageAnnualSetting(HalfDayManage halfDayManage, RemainingNumberSetting remainingNumberSetting,
			YearLyOfNumberDays yearlyOfNumberDays) {
		super();
		this.halfDayManage = halfDayManage;
		this.remainingNumberSetting = remainingNumberSetting;
		this.yearlyOfNumberDays = yearlyOfNumberDays;
	}
    
    /**
     * [1] 半日回数上限に対応する月次の勤怠項目を取得する
     */
    public List<Integer> getMonthlyAttendanceItemsHalfDayLimit() {
    	return halfDayManage.getMonthlyAttendanceItemsCorresHalfDayLimit();
    }
    
    /**
     * [2] 利用できない月次の勤怠項目を取得する
     */
    public List<Integer> getMonthlyAttendanceItems(ManageDistinct timeManageType) {
    	return halfDayManage.getMonthlyAttendanceItems(timeManageType);
    }

    /**
     * Instantiates a new manage annual setting.
     *
     * @param memento the memento
     */
    public ManageAnnualSetting(ManageAnnualSettingGetMemento memento) {
        super();
//        this.maxGrantDay = memento.getMaxGrantDay();
        this.halfDayManage = memento.getHalfDayManage();
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
        if(this.remainingNumberSetting == null){
        	memento.setRemainingNumberSetting(new RemainingNumberSetting(new RetentionYear(2)));
        }else{
        memento.setRemainingNumberSetting(this.remainingNumberSetting);}
        if(this.yearlyOfNumberDays == null){
        	memento.setYearLyOfDays(new YearLyOfNumberDays(new Double(0)));
        }else{
        memento.setYearLyOfDays(this.yearlyOfNumberDays);}
        
    }
    /**
     * [3] 半日年休上限回数を取得
     * @param fromGrantTableCount
     * @return
     */
	 public Optional<LimitedHalfHdCnt> getLimitedHalfCount(Optional<LimitedHalfHdCnt> fromGrantTableCount){
		 return this.getHalfDayManage().getLimitedHalfCount(fromGrantTableCount);
	 }
	 
}
