package nts.uk.ctx.at.record.dom.workinformation;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workinformation.WorkInfoChangeEvent;

/**
 * 
 * @author nampt
 * 日別実績の勤務情報 - root
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkInfoOfDailyPerformance extends AggregateRoot {

	//社員ID
	private String employeeId;
	//年月日
	private GeneralDate ymd;
	//勤務情報
	private WorkInfoOfDailyAttendance workInformation;

    public WorkInfoOfDailyPerformance(String employeeId, WorkInformation recordWorkInformation,
            WorkInformation scheduleWorkInformation, CalculationState calculationState, NotUseAttribute goStraightAtr,
            NotUseAttribute backStraightAtr, GeneralDate ymd, List<ScheduleTimeSheet> scheduleTimeSheets) {
        this.employeeId = employeeId;
        this.ymd = ymd;
        this.workInformation = new WorkInfoOfDailyAttendance(
        		recordWorkInformation,
        		scheduleWorkInformation,
        		calculationState,
        		goStraightAtr,
        		backStraightAtr,
                DayOfWeek.MONDAY, //一時対応
                scheduleTimeSheets
                );
    } 
	public WorkInfoOfDailyPerformance(String employeeId, GeneralDate ymd,WorkInfoOfDailyAttendance workInfo) {
		this.employeeId = employeeId;
		this.ymd = ymd;
		setWorkInformation(workInfo);
	} 
	
	/**
	 * 勤務予定を実績に移す
	 */
	public void shiftFromScheduleToRecord() {
		this.workInformation.setRecordInfo(workInformation.getScheduleInfo());
	}
	
	/** <<Event>> 実績の就業時間帯が変更されたを発行する */
	public void workTimeChanged() {
		WorkInfoChangeEvent.builder().employeeId(employeeId).targetDate(ymd)
				.newWorkTimeCode(workInformation.getRecordInfo() == null ? null : workInformation.getRecordInfo().getWorkTimeCode()).build().toBePublished();
	}
	
	/** <<Event>> 実績の勤務種類が変更されたを発行する */
	public void workTypeChanged() {
		WorkInfoChangeEvent.builder().employeeId(employeeId).targetDate(ymd)
				.newWorkTypeCode(workInformation.getRecordInfo() == null ? null : workInformation.getRecordInfo().getWorkTypeCode()).build().toBePublished();
	}

	/** <<Event>> 実績の就業時間帯が変更されたを発行する */
	/** <<Event>> 実績の勤務種類が変更されたを発行する */
	public void workInfoChanged() {
		WorkInfoChangeEvent.builder().employeeId(employeeId).targetDate(ymd)
				.newWorkTypeCode(workInformation.getRecordInfo() == null ? null : workInformation.getRecordInfo().getWorkTypeCode())
				.newWorkTimeCode(workInformation.getRecordInfo() == null ? null : workInformation.getRecordInfo().getWorkTimeCode())
				.build().toBePublished();
	}

	/**
	 * 勤務予定の勤務情報と勤務実績の勤務情報が同じかどうか確認する
	 * @param workNo
	 * @param predetermineTimeSheetSetting
	 * @return
	 */
	public boolean isMatchWorkInfomation() {			
		return workInformation.isMatchWorkInfomation();
	}

	/**
	 * 指定された勤務回数の予定時間帯を取得する
	 * @param workNo
	 * @return　予定時間帯
	 */
	public Optional<ScheduleTimeSheet> getScheduleTimeSheet(WorkNo workNo) {
		return workInformation.getScheduleTimeSheets().stream()
				.filter(ts -> ts.getWorkNo().equals(workNo)).findFirst();	
	}

	public WorkInfoOfDailyPerformance(String employeeId, WorkInformation recordWorkInformation,
			WorkInformation scheduleWorkInformation, CalculationState calculationState, NotUseAttribute goStraightAtr,
			NotUseAttribute backStraightAtr, GeneralDate ymd, DayOfWeek dayOfWeek,
			List<ScheduleTimeSheet> scheduleTimeSheets) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workInformation = new WorkInfoOfDailyAttendance(
				recordWorkInformation,
				scheduleWorkInformation,
				calculationState,
				goStraightAtr,
				backStraightAtr,
				dayOfWeek,
				scheduleTimeSheets
				);
	}
	
	/**
	 * 計算ステータスの変更
	 * @param state 計算ステータス
	 */
	public void changeCalcState(CalculationState state) {
		workInformation.setCalculationState(state);
	}
	public WorkInfoOfDailyPerformance(String employeeId, GeneralDate ymd) {
		this.employeeId = employeeId;
		this.ymd = ymd;
	}
	@Override
	public void setVersion(long version) {
		super.setVersion(version);
		if (this.workInformation != null) {
			this.workInformation.setVer(version);
		}
	}
	
	public void setWorkInformation(WorkInfoOfDailyAttendance info) {
		this.workInformation = info;
		setVersion(info.getVer());
	}
	
	
}
