package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.List;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

@Stateless
public class DailyRecordWorkCommandHandler {

	/** 勤務情報： 日別実績の勤務情報 */
	@Inject
	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
	private WorkInformationOfDailyPerformCommandAddHandler workInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "A", jpPropertyName = "", isUpdate = true)
	private WorkInformationOfDailyPerformCommandUpdateHandler workInfoUpdateHandler;

	/** 計算区分： 日別実績の計算区分 */
	@Inject
	@AttendanceItemLayout(layout = "B", jpPropertyName = "")
	private CalcAttrOfDailyPerformanceCommandAddHandler calcAttrAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "B", jpPropertyName = "", isUpdate = true)
	private CalcAttrOfDailyPerformanceCommandUpdateHandler calcAttrUpdateHandler;

	/** 所属情報： 日別実績の所属情報 */
	@Inject
	@AttendanceItemLayout(layout = "C", jpPropertyName = "")
	private AffiliationInforOfDailyPerformCommandAddHandler affiliationInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "C", jpPropertyName = "", isUpdate = true)
	private AffiliationInforOfDailyPerformCommandUpdateHandler affiliationInfoUpdateHandler;

	/** エラー一覧： 社員の日別実績エラー一覧 */
	@Inject
	@AttendanceItemLayout(layout = "D", jpPropertyName = "")
	private EmployeeDailyPerErrorCommandAddHandler errorAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "D", jpPropertyName = "", isUpdate = true)
	private EmployeeDailyPerErrorCommandUpdateHandler errorUpdateHandler;

	/** 外出時間帯: 日別実績の外出時間帯 */
	@Inject
	@AttendanceItemLayout(layout = "E", jpPropertyName = "")
	private OutingTimeOfDailyPerformanceCommandAddHandler outingTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "E", jpPropertyName = "", isUpdate = true)
	private OutingTimeOfDailyPerformanceCommandUpdateHandler outingTimeUpdateHandler;

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@Inject
	@AttendanceItemLayout(layout = "F", jpPropertyName = "")
	private BreakTimeOfDailyPerformanceCommandAddHandler breakTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "F", jpPropertyName = "", isUpdate = true)
	private BreakTimeOfDailyPerformanceCommandUpdateHandler breakTimeUpdateHandler;

	/** 勤怠時間: 日別実績の勤怠時間 */
	@Inject
	@AttendanceItemLayout(layout = "G", jpPropertyName = "")
	private AddAttendanceTimeOfDailyPerformCommandAddHandler attendanceTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "G", jpPropertyName = "", isUpdate = true)
	private AddAttendanceTimeOfDailyPerformCommandUpdateHandler attendanceTimeUpdateHandler;

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@Inject
	@AttendanceItemLayout(layout = "H", jpPropertyName = "")
	private AttendanceTimeByWorkOfDailyCommandAddHandler attendanceTimeByWorkAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "H", jpPropertyName = "", isUpdate = true)
	private AttendanceTimeByWorkOfDailyCommandUpdateHandler attendanceTimeByWorkUpdateHandler;

	/** 出退勤: 日別実績の出退勤 */
	@Inject
	@AttendanceItemLayout(layout = "I", jpPropertyName = "")
	private TimeLeavingOfDailyPerformanceCommandAddHandler timeLeavingAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "I", jpPropertyName = "", isUpdate = true)
	private TimeLeavingOfDailyPerformanceCommandUpdateHandler timeLeavingUpdatedHandler;

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@Inject
	@AttendanceItemLayout(layout = "J", jpPropertyName = "")
	private ShortTimeOfDailyCommandAddHandler shortWorkTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "J", jpPropertyName = "", isUpdate = true)
	private ShortTimeOfDailyCommandUpdateHandler shortWorkTimeUpdateHandler;

	/** 特定日区分: 日別実績の特定日区分 */
	@Inject
	@AttendanceItemLayout(layout = "K", jpPropertyName = "")
	private SpecificDateAttrOfDailyCommandAddHandler specificDateAttrAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "K", jpPropertyName = "", isUpdate = true)
	private SpecificDateAttrOfDailyCommandUpdateHandler specificDateAttrUpdateHandler;

	/** 入退門: 日別実績の入退門 */
	@Inject
	@AttendanceItemLayout(layout = "L", jpPropertyName = "")
	private AttendanceLeavingGateOfDailyCommandAddHandler attendanceLeavingGateAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "L", jpPropertyName = "", isUpdate = true)
	private AttendanceLeavingGateOfDailyCommandUpdateHandler attendanceLeavingGateUpdateHandler;

	/** 任意項目: 日別実績の任意項目 */
	@Inject
	@AttendanceItemLayout(layout = "M", jpPropertyName = "")
	private OptionalItemOfDailyPerformCommandAddHandler optionalItemAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "M", jpPropertyName = "", isUpdate = true)
	private OptionalItemOfDailyPerformCommandUpdateHandler optionalItemUpdateHandler;

	/** 編集状態: 日別実績の編集状態 */
	@Inject
	//@AttendanceItemLayout(layout = "N", jpPropertyName = "")
	private EditStateOfDailyPerformCommandAddHandler editStateAddHandler;
	@Inject
	//@AttendanceItemLayout(layout = "N", jpPropertyName = "", isUpdate = true)
	private EditStateOfDailyPerformCommandUpdateHandler editStateUpdateHandler;

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@Inject
	@AttendanceItemLayout(layout = "O", jpPropertyName = "")
	private TemporaryTimeOfDailyPerformanceCommandAddHandler temporaryTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = "O", jpPropertyName = "", isUpdate = true)
	private TemporaryTimeOfDailyPerformanceCommandUpdateHandler temporaryTimeUpdateHandler;

	public void handleAdd(DailyRecordWorkCommand command) {
		handler(command, false);
//		this.attendanceTimeAddHandler.handle(command.getAttendanceTimeCommand());
//		this.workInfoAddHandler.handle(command.getWorkInfoCommand());
//		this.calcAttrAddHandler.handle(command.getCalcAttrCommand());
//		this.affiliationInfoAddHandler.handle(command.getAffiliationInfoCommand());
//		this.errorAddHandler.handle(command.getErrorCommand());
//		this.outingTimeAddHandler.handle(command.getOutingTimeCommand());
//		this.breakTimeAddHandler.handle(command.getBreakTimeCommand());
//		this.attendanceTimeByWorkAddHandler.handle(command.getAttendanceTimeByWorkCommand());
//		this.timeLeavingAddHandler.handle(command.getTimeLeavingCommand());
//		this.shortWorkTimeAddHandler.handle(command.getShortWorkTimeCommand());
//		this.specificDateAttrAddHandler.handle(command.getSpecificDateAttrCommand());
//		this.attendanceLeavingGateAddHandler.handle(command.getAttendanceLeavingGateCommand());
//		this.optionalItemAddHandler.handle(command.getOptionalItemCommand());
//		this.editStateAddHandler.handle(command.getEditStateCommand());
//		this.temporaryTimeAddHandler.handle(command.getTemporaryTimeCommand());
	}
	
	public void handleUpdate(DailyRecordWorkCommand command) {
		handler(command, true);
//		this.attendanceTimeUpdateHandler.handle(command.getAttendanceTimeCommand());
//		this.workInfoUpdateHandler.handle(command.getWorkInfoCommand());
//		this.calcAttrUpdateHandler.handle(command.getCalcAttrCommand());
//		this.affiliationInfoUpdateHandler.handle(command.getAffiliationInfoCommand());
//		this.errorUpdateHandler.handle(command.getErrorCommand());
//		this.outingTimeUpdateHandler.handle(command.getOutingTimeCommand());
//		this.breakTimeUpdateHandler.handle(command.getBreakTimeCommand());
//		this.attendanceTimeByWorkUpdateHandler.handle(command.getAttendanceTimeByWorkCommand());
//		this.timeLeavingUpdatedHandler.handle(command.getTimeLeavingCommand());
//		this.shortWorkTimeUpdateHandler.handle(command.getShortWorkTimeCommand());
//		this.specificDateAttrUpdateHandler.handle(command.getSpecificDateAttrCommand());
//		this.attendanceLeavingGateUpdateHandler.handle(command.getAttendanceLeavingGateCommand());
//		this.optionalItemUpdateHandler.handle(command.getOptionalItemCommand());
//		this.editStateUpdateHandler.handle(command.getEditStateCommand());
//		this.temporaryTimeUpdateHandler.handle(command.getTemporaryTimeCommand());
	}

	@SuppressWarnings({ "unchecked" })
	private <T extends DailyWorkCommonCommand> void handler(DailyRecordWorkCommand command, boolean isUpdate) {
		List<String> mapped = command.itemValues().stream().map(c -> getGroup(c))
				.distinct().collect(Collectors.toList());
		mapped.stream().forEach(c -> {
			CommandFacade<T> handler = (CommandFacade<T>) getHandler(c, isUpdate);
			if(handler != null){
				handler.handle((T) command.getCommand(c));
			}
		});
	}
	
	private CommandFacade<?> getHandler(String group, boolean isUpdate) {
		CommandFacade<?> handler = null;
		switch (group) {
		case "A":
			handler = isUpdate ? this.workInfoUpdateHandler : this.workInfoAddHandler;
			break;
		case "B":
			handler = isUpdate ? this.calcAttrUpdateHandler : this.calcAttrAddHandler;
			break;
		case "C":
			handler = isUpdate ? this.affiliationInfoUpdateHandler : this.affiliationInfoAddHandler;
			break;
		case "D":
			handler = isUpdate ? this.errorUpdateHandler : this.errorAddHandler;
			break;
		case "E":
			handler = isUpdate ? this.outingTimeUpdateHandler : this.outingTimeAddHandler;
			break;
		case "F":
			handler = isUpdate ? this.breakTimeUpdateHandler : this.breakTimeAddHandler;
			break;
		case "G":
			//
			handler = isUpdate ? this.attendanceTimeUpdateHandler : this.attendanceTimeAddHandler;
			break;
		case "H":
			handler = isUpdate ? this.attendanceTimeByWorkUpdateHandler : this.attendanceTimeByWorkAddHandler;
			break;
		case "I":
			handler = isUpdate ? this.timeLeavingUpdatedHandler : this.timeLeavingAddHandler;
			break;
		case "J":
			handler = isUpdate ? this.shortWorkTimeUpdateHandler : this.shortWorkTimeAddHandler;
			break;
		case "K":
			handler = isUpdate ? this.specificDateAttrUpdateHandler : this.specificDateAttrAddHandler;
			break;
		case "L":
			handler = isUpdate ? this.attendanceLeavingGateUpdateHandler : this.attendanceLeavingGateAddHandler;
			break;
		case "M":
			handler = isUpdate ? this.optionalItemUpdateHandler : this.optionalItemAddHandler;
			break;
		case "N":
			handler = isUpdate ? this.editStateUpdateHandler : this.editStateAddHandler;
			break;
		case "O":
			handler = isUpdate ? this.temporaryTimeUpdateHandler : this.temporaryTimeAddHandler;
			break;
		default:
			break;
		}
		return handler;
	}
	
//	private CommandFacade<?> getHandler(String group, boolean isUpdate){
//		return (CommandFacade<?>) ReflectionUtil.getStreamOfFieldsAnnotated(getClass(), Condition.ALL, AttendanceItemLayout.class).filter(c -> {
//			AttendanceItemLayout layout = c.getAnnotation(AttendanceItemLayout.class);
//			return layout.isUpdate() == isUpdate && layout.layout().equals(group);
//		}).findFirst().map(c -> ReflectionUtil.getFieldValue(c, this)).orElse(null);
//	}

	private String getGroup(ItemValue c) {
		return String.valueOf(c.getLayoutCode().charAt(0));
	}

}
