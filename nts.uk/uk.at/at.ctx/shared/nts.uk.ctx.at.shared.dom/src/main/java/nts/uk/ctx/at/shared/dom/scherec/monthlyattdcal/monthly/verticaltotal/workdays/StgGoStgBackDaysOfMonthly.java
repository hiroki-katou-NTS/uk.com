package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 月別実績の直行直帰日数
 */
@Getter
public class StgGoStgBackDaysOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 直行日数 */
	private AttendanceDaysMonth straightGo;
	/** 直帰日数 */
	private AttendanceDaysMonth straightBack;
	/** 直行直帰日数 */
	private AttendanceDaysMonth straightGoStraightBack;
	
	/**
	 * コンストラクタ
	 */
	public StgGoStgBackDaysOfMonthly(){
		
		this.straightGo = new AttendanceDaysMonth(0.0);
		this.straightBack = new AttendanceDaysMonth(0.0);
		this.straightGoStraightBack = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param straightGo 直行日数
	 * @param straightBack　直帰日数
	 * @param straightGoStraightBack 直行直帰日数
	 * @return 月別実績の直行直帰日数
	 */
	public static StgGoStgBackDaysOfMonthly of(
			AttendanceDaysMonth straightGo,
			AttendanceDaysMonth straightBack,
			AttendanceDaysMonth straightGoStraightBack){
		
		val domain = new StgGoStgBackDaysOfMonthly();
		domain.straightGo = straightGo;
		domain.straightBack = straightBack;
		domain.straightGoStraightBack = straightGoStraightBack;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workInfo 日別勤怠の勤務情報
	 */
	public void aggregate(WorkInfoOfDailyAttendance workInfo){
		if (workInfo.getBackStraightAtr() == NotUseAttribute.Use) {
			this.straightBack = this.straightBack.addDays(1d);
		}
		if (workInfo.getGoStraightAtr() == NotUseAttribute.Use) {
			this.straightGo = this.straightGo.addDays(1d);
		}
		if (workInfo.getBackStraightAtr() == NotUseAttribute.Use
				&& workInfo.getGoStraightAtr() == NotUseAttribute.Use) {
			this.straightGoStraightBack = this.straightGoStraightBack.addDays(1d);
		}
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(StgGoStgBackDaysOfMonthly target){
		
		this.straightGo = this.straightGo.addDays(target.straightGo.v());
		this.straightBack = this.straightBack.addDays(target.straightBack.v());
		this.straightGoStraightBack = this.straightGoStraightBack.addDays(target.straightGoStraightBack.v());
	}
}
