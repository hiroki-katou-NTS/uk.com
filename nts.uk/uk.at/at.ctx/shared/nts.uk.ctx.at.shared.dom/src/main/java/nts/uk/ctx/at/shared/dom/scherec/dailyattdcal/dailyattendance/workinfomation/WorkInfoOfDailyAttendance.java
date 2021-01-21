package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

/**
 * 日別勤怠の勤務情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.勤務情報.日別勤怠の勤務情報
 * @author tutk
 * 
 *
 */
@Getter
@NoArgsConstructor
public class WorkInfoOfDailyAttendance implements DomainObject {

	@Setter
	// 勤務実績の勤務情報
	private WorkInformation recordInfo;
	@Setter
	// 勤務予定の勤務情報
	private WorkInformation scheduleInfo;
	@Setter
	// 計算状態
	private CalculationState calculationState;
	// 直行区分
	private NotUseAttribute goStraightAtr;
	// 直帰区分
	private NotUseAttribute backStraightAtr;
	// 曜日
	private DayOfWeek dayOfWeek;
	// 勤務予定時間帯
	private List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();
	//Ver
	@Setter
	@Getter
	private long ver;
	public WorkInfoOfDailyAttendance(WorkInformation recordInfo, WorkInformation scheduleInfo,
			CalculationState calculationState, NotUseAttribute goStraightAtr, NotUseAttribute backStraightAtr,
			DayOfWeek dayOfWeek, List<ScheduleTimeSheet> scheduleTimeSheets) {
		super();
		this.recordInfo = recordInfo;
		this.scheduleInfo = scheduleInfo;
		this.calculationState = calculationState;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.dayOfWeek = dayOfWeek;
		this.scheduleTimeSheets = scheduleTimeSheets;
	}
	
	/**
	 * 計算ステータスの変更
	 * @param state 計算ステータス
	 */
	public void changeCalcState(CalculationState state) {
		this.setCalculationState(state);
	}
	
	/**
	 * 指定された勤務回数の予定時間帯を取得する
	 * 
	 * @param workNo
	 * @return　予定時間帯
	 */
	public Optional<ScheduleTimeSheet> getScheduleTimeSheet(WorkNo workNo) {
		return this.scheduleTimeSheets.stream()
				.filter(ts -> ts.getWorkNo().equals(workNo)).findFirst();	
	}

	public void setGoStraightAtr(NotUseAttribute goStraightAtr) {
		this.goStraightAtr = goStraightAtr;
	}

	public void setBackStraightAtr(NotUseAttribute backStraightAtr) {
		this.backStraightAtr = backStraightAtr;
	}

	public void setScheduleTimeSheets(List<ScheduleTimeSheet> scheduleTimeSheets) {
		this.scheduleTimeSheets = scheduleTimeSheets;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	/**
	 * [2] 出勤・休日系の判定																							
	 * @param require
	 * @return
	 */
	public Optional<WorkStyle> getWorkStyle(Require require){
		return this.recordInfo.getWorkStyle(require);
	}

	/**
	 * 勤務予定の勤務情報と勤務実績の勤務情報が同じかどうか確認する
	 * @param workNo
	 * @param predetermineTimeSheetSetting
	 * @return
	 */
	public boolean isMatchWorkInfomation() {			
		if(getScheduleInfo().getWorkTypeCode() == getRecordInfo().getWorkTypeCode()&&
				getScheduleInfo().getWorkTimeCode() == getRecordInfo().getWorkTimeCode()) {
			return true;
		}
		return false;
	}
	
	public static interface Require extends WorkInformation.Require {
		
	}
	
}
