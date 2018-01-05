package nts.uk.ctx.at.record.app.command.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AddAttendanceTimeOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AddAttendanceTimeOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.erroralarm.EmployeeDailyPerErrorCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.erroralarm.EmployeeDailyPerErrorCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommandUpdateHandler;

@Stateless
public class DailyRecordWorkCommandHandler {

	@Inject
	/** 勤怠時間: 日別実績の勤怠時間 */
	private AddAttendanceTimeOfDailyPerformCommandAddHandler attendanceTimeAddHandler;
	@Inject
	private AddAttendanceTimeOfDailyPerformCommandUpdateHandler attendanceTimeUpdateHandler;

	@Inject
	/** 勤務情報： 日別実績の勤務情報 */
	private WorkInformationOfDailyPerformCommandAddHandler workInfoAddHandler;
	@Inject
	private WorkInformationOfDailyPerformCommandUpdateHandler workInfoUpdateHandler;

	@Inject
	/** 計算区分： 日別実績の計算区分 */
	private CalcAttrOfDailyPerformanceCommandAddHandler calcAttrAddHandler;
	@Inject
	private CalcAttrOfDailyPerformanceCommandUpdateHandler calcAttrUpdateHandler;

	@Inject
	/** 所属情報： 日別実績の所属情報 */
	private AffiliationInforOfDailyPerformCommandAddHandler affiliationInfoAddHandler;
	@Inject
	private AffiliationInforOfDailyPerformCommandUpdateHandler affiliationInfoUpdateHandler;

	@Inject
	/** エラー一覧： 社員の日別実績エラー一覧 */
	private EmployeeDailyPerErrorCommandAddHandler errorAddHandler;
	@Inject
	private EmployeeDailyPerErrorCommandUpdateHandler errorUpdateHandler;

	@Inject
	/** 外出時間帯: 日別実績の外出時間帯 */
	private OutingTimeOfDailyPerformanceCommandAddHandler outingTimeAddHandler;
	@Inject
	private OutingTimeOfDailyPerformanceCommandUpdateHandler outingTimeUpdateHandler;

	@Inject
	/** 休憩時間帯: 日別実績の休憩時間帯 */
	private BreakTimeOfDailyPerformanceCommandAddHandler breakTimeAddHandler;
	@Inject
	private BreakTimeOfDailyPerformanceCommandUpdateHandler breakTimeUpdateHandler;

	@Inject
	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	private AttendanceTimeByWorkOfDailyCommandAddHandler attendanceTimeByWorkAddHandler;
	@Inject
	private AttendanceTimeByWorkOfDailyCommandUpdateHandler attendanceTimeByWorkUpdateHandler;

	@Inject
	/** 出退勤: 日別実績の出退勤 */
	private TimeLeavingOfDailyPerformanceCommandAddHandler timeLeavingAddHandler;
	@Inject
	private TimeLeavingOfDailyPerformanceCommandUpdateHandler timeLeavingUpdatedHandler;

	@Inject
	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	private ShortTimeOfDailyCommandAddHandler shortWorkTimeAddHandler;
	@Inject
	private ShortTimeOfDailyCommandUpdateHandler shortWorkTimeUpdateHandler;

	@Inject
	/** 特定日区分: 日別実績の特定日区分 */
	private SpecificDateAttrOfDailyCommandAddHandler specificDateAttrAddHandler;
	@Inject
	private SpecificDateAttrOfDailyCommandUpdateHandler specificDateAttrUpdateHandler;

	@Inject
	/** 入退門: 日別実績の入退門 */
	private AttendanceLeavingGateOfDailyCommandAddHandler attendanceLeavingGateAddHandler;
	@Inject
	private AttendanceLeavingGateOfDailyCommandUpdateHandler attendanceLeavingGateUpdateHandler;

	@Inject
	/** 任意項目: 日別実績の任意項目 */
	private OptionalItemOfDailyPerformCommandAddHandler optionalItemAddHandler;
	@Inject
	private OptionalItemOfDailyPerformCommandUpdateHandler optionalItemUpdateHandler;

	@Inject
	/** 編集状態: 日別実績の編集状態 */
	private EditStateOfDailyPerformCommandAddHandler editStateAddHandler;
	@Inject
	private EditStateOfDailyPerformCommandUpdateHandler editStateUpdateHandler;

	@Inject
	/** 臨時出退勤: 日別実績の臨時出退勤 */
	private TemporaryTimeOfDailyPerformanceCommandAddHandler temporaryTimeAddHandler;
	@Inject
	private TemporaryTimeOfDailyPerformanceCommandUpdateHandler temporaryTimeUpdateHandler;

	public void handleAdd(DailyRecordWorkCommand command) {
		this.attendanceTimeAddHandler.handle(command.getAttendanceTimeCommand());
		this.workInfoAddHandler.handle(command.getWorkInfoCommand());
		this.calcAttrAddHandler.handle(command.getCalcAttrCommand());
		this.affiliationInfoAddHandler.handle(command.getAffiliationInfoCommand());
		this.errorAddHandler.handle(command.getErrorCommand());
		this.outingTimeAddHandler.handle(command.getOutingTimeCommand());
		this.breakTimeAddHandler.handle(command.getBreakTimeCommand());
		this.attendanceTimeByWorkAddHandler.handle(command.getAttendanceTimeByWorkCommand());
		this.timeLeavingAddHandler.handle(command.getTimeLeavingCommand());
		this.shortWorkTimeAddHandler.handle(command.getShortWorkTimeCommand());
		this.specificDateAttrAddHandler.handle(command.getSpecificDateAttrCommand());
		this.attendanceLeavingGateAddHandler.handle(command.getAttendanceLeavingGateCommand());
		this.optionalItemAddHandler.handle(command.getOptionalItemCommand());
		this.editStateAddHandler.handle(command.getEditStateCommand());
		this.temporaryTimeAddHandler.handle(command.getTemporaryTimeCommand());
	}
	
	public void handleUpdate(DailyRecordWorkCommand command) {
		this.attendanceTimeUpdateHandler.handle(command.getAttendanceTimeCommand());
		this.workInfoUpdateHandler.handle(command.getWorkInfoCommand());
		this.calcAttrUpdateHandler.handle(command.getCalcAttrCommand());
		this.affiliationInfoUpdateHandler.handle(command.getAffiliationInfoCommand());
		this.errorUpdateHandler.handle(command.getErrorCommand());
		this.outingTimeUpdateHandler.handle(command.getOutingTimeCommand());
		this.breakTimeUpdateHandler.handle(command.getBreakTimeCommand());
		this.attendanceTimeByWorkUpdateHandler.handle(command.getAttendanceTimeByWorkCommand());
		this.timeLeavingUpdatedHandler.handle(command.getTimeLeavingCommand());
		this.shortWorkTimeUpdateHandler.handle(command.getShortWorkTimeCommand());
		this.specificDateAttrUpdateHandler.handle(command.getSpecificDateAttrCommand());
		this.attendanceLeavingGateUpdateHandler.handle(command.getAttendanceLeavingGateCommand());
		this.optionalItemUpdateHandler.handle(command.getOptionalItemCommand());
		this.editStateUpdateHandler.handle(command.getEditStateCommand());
		this.temporaryTimeUpdateHandler.handle(command.getTemporaryTimeCommand());
	}

}
