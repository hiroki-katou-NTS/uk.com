package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.RestTimeZoneOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
/** 日別実績（WORK） */
@AttendanceItemRoot(isContainer = true)
public class DailyRecordDto implements ConvertibleAttendanceItem {

	/** 勤務情報： 日別実績の勤務情報 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
	private WorkInformationOfDailyDto workInfo;

	/** 計算区分： 日別実績の計算区分 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "")
	private CalcAttrOfDailyPerformanceDto calcAttr;

	/** 所属情報： 日別実績の所属情報 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "")
	private AffiliationInforOfDailyPerforDto affiliationInfo;

	/** エラー一覧： 社員の日別実績エラー一覧 */
	// TODO: list?
	@AttendanceItemLayout(layout = "D", jpPropertyName = "")
	private EmployeeDailyPerErrorDto errors;

	/** 外出時間帯: 日別実績の外出時間帯 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "", isOptional = true)
	private Optional<OutingTimeOfDailyPerformanceDto> outingTime = Optional.empty();

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "", isList = true, listMaxLength = 2, setFieldWithIndex = "restTimeType")
	private List<RestTimeZoneOfDailyDto> breakTime= new ArrayList<>();

	/** 勤怠時間: 日別実績の勤怠時間 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "", isOptional = true)
	private Optional<AttendanceTimeDailyPerformDto> attendanceTime = Optional.empty();

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@AttendanceItemLayout(layout = "H", jpPropertyName = "", isOptional = true)
	private Optional<AttendanceTimeByWorkOfDailyDto> attendanceTimeByWork = Optional.empty();

	/** 出退勤: 日別実績の出退勤 */
	@AttendanceItemLayout(layout = "I", jpPropertyName = "", isOptional = true)
	private Optional<TimeLeavingOfDailyPerformanceDto> timeLeaving = Optional.empty();

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@AttendanceItemLayout(layout = "J", jpPropertyName = "", isOptional = true)
	private Optional<ShortTimeOfDailyDto> shortWorkTime = Optional.empty();

	/** 特定日区分: 日別実績の特定日区分 */
	@AttendanceItemLayout(layout = "K", jpPropertyName = "", isOptional = true)
	private Optional<SpecificDateAttrOfDailyPerforDto> specificDateAttr = Optional.empty();

	/** 入退門: 日別実績の入退門 */
	@AttendanceItemLayout(layout = "L", jpPropertyName = "", isOptional = true)
	private Optional<AttendanceLeavingGateOfDailyDto> attendanceLeavingGate = Optional.empty();

	/** 任意項目: 日別実績の任意項目 */
	@AttendanceItemLayout(layout = "M", jpPropertyName = "", isOptional = true)
	private Optional<OptionalItemOfDailyPerformDto> optionalItem = Optional.empty();

	/** 編集状態: 日別実績の編集状態 */
	//@AttendanceItemLayout(layout = "N", jpPropertyName = "", isList = true, listMaxLength = ?)
	private List<EditStateOfDailyPerformanceDto> editStates = new ArrayList<>();

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@AttendanceItemLayout(layout = "O", jpPropertyName = "", isOptional = true)
	private Optional<TemporaryTimeOfDailyPerformanceDto> temporaryTime = Optional.empty();
}
