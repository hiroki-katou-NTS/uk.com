package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto.PCLogOnInforOfDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarksOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

@Data
/** 日別実績（WORK） */
@AttendanceItemRoot(isContainer = true)
public class DailyRecordDto extends AttendanceItemCommon {

	private String employeeId;

	private GeneralDate date;

	/** 勤務情報： 日別実績の勤務情報 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "日別実績の勤務情報")
	private WorkInformationOfDailyDto workInfo;

	/** 計算区分： 日別実績の計算区分 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "日別実績の計算区分")
	private CalcAttrOfDailyPerformanceDto calcAttr;

	/** 所属情報： 日別実績の所属情報 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "日別実績の所属情報")
	private AffiliationInforOfDailyPerforDto affiliationInfo;

	/** 日別実績の勤務種別 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "日別実績の勤務種別", isOptional = true)
	private Optional<BusinessTypeOfDailyPerforDto> businessType = Optional.empty();

	/** エラー一覧： 社員の日別実績エラー一覧 */
	// TODO: list?
	// @AttendanceItemLayout(layout = "D", jpPropertyName = "社員の日別実績エラー一覧")
	private EmployeeDailyPerErrorDto errors;

	/** 外出時間帯: 日別実績の外出時間帯 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "日別実績の外出時間帯", isOptional = true)
	private Optional<OutingTimeOfDailyPerformanceDto> outingTime = Optional.empty();

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "日別実績の休憩時間帯", listMaxLength = 2, enumField = "restTimeType", listNoIndex = true)
	private List<BreakTimeDailyDto> breakTime = new ArrayList<>();

	/** 勤怠時間: 日別実績の勤怠時間 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "日別実績の勤怠時間", isOptional = true)
	private Optional<AttendanceTimeDailyPerformDto> attendanceTime = Optional.empty();

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@AttendanceItemLayout(layout = "H", jpPropertyName = "日別実績の作業別勤怠時間", isOptional = true)
	private Optional<AttendanceTimeByWorkOfDailyDto> attendanceTimeByWork = Optional.empty();

	/** 出退勤: 日別実績の出退勤 */
	@AttendanceItemLayout(layout = "I", jpPropertyName = "日別実績の出退勤", isOptional = true)
	private Optional<TimeLeavingOfDailyPerformanceDto> timeLeaving = Optional.empty();

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@AttendanceItemLayout(layout = "J", jpPropertyName = "日別実績の短時間勤務時間帯", isOptional = true)
	private Optional<ShortTimeOfDailyDto> shortWorkTime = Optional.empty();

	/** 特定日区分: 日別実績の特定日区分 */
	@AttendanceItemLayout(layout = "K", jpPropertyName = "日別実績の特定日区分", isOptional = true)
	private Optional<SpecificDateAttrOfDailyPerforDto> specificDateAttr = Optional.empty();

	/** 入退門: 日別実績の入退門 */
	@AttendanceItemLayout(layout = "L", jpPropertyName = "日別実績の入退門", isOptional = true)
	private Optional<AttendanceLeavingGateOfDailyDto> attendanceLeavingGate = Optional.empty();

	/** 任意項目: 日別実績の任意項目 */
	@AttendanceItemLayout(layout = "M", jpPropertyName = "日別実績の任意項目", isOptional = true)
	private Optional<OptionalItemOfDailyPerformDto> optionalItem = Optional.empty();

	/** 編集状態: 日別実績の編集状態 */
	// @AttendanceItemLayout(layout = "N", jpPropertyName = "日別実績の編集状態", isList =
	// true, listMaxLength = ?)
	private List<EditStateOfDailyPerformanceDto> editStates = new ArrayList<>();

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@AttendanceItemLayout(layout = "O", jpPropertyName = "日別実績の臨時出退勤", isOptional = true)
	private Optional<TemporaryTimeOfDailyPerformanceDto> temporaryTime = Optional.empty();
	/** PCログオン情報: 日別実績のPCログオン情報 */
	@AttendanceItemLayout(layout = "P", jpPropertyName = "日別実績のPCログオン情報", isOptional = true)
	private Optional<PCLogOnInforOfDailyPerformDto> pcLogInfo = Optional.empty();
	
	/** 備考: 日別実績の備考 */
	@AttendanceItemLayout(layout = "Q", jpPropertyName = "日別実績の備考", listMaxLength = 5, indexField = "remarkNo")
	private List<RemarksOfDailyDto> remarks = new ArrayList<>();

	public static DailyRecordDto from(IntegrationOfDaily domain){
		DailyRecordDto dto = new DailyRecordDto();
		if(domain != null){
			dto.setWorkInfo(WorkInformationOfDailyDto.getDto(domain.getWorkInformation()));
			dto.setCalcAttr(CalcAttrOfDailyPerformanceDto.getDto(domain.getCalAttr()));
			dto.setAffiliationInfo(AffiliationInforOfDailyPerforDto.getDto(domain.getAffiliationInfor()));
			dto.setBusinessType(domain.getBusinessType().map(b -> BusinessTypeOfDailyPerforDto.getDto(b)));
			if(domain.getEmployeeError() != null && !domain.getEmployeeError().isEmpty()) {
				dto.setErrors(EmployeeDailyPerErrorDto.getDto(domain.getEmployeeError().get(0)));
			}
			dto.setOutingTime(domain.getOutingTime().map(o -> OutingTimeOfDailyPerformanceDto.getDto(o)));
			dto.setBreakTime(domain.getBreakTime().stream().map(b -> BreakTimeDailyDto.getDto(b)).collect(Collectors.toList()));
			dto.setAttendanceTime(domain.getAttendanceTimeOfDailyPerformance().map(a -> AttendanceTimeDailyPerformDto.getDto(a)));
			dto.setAttendanceTimeByWork(domain.getAttendancetimeByWork().map(a -> AttendanceTimeByWorkOfDailyDto.getDto(a)));
			dto.setTimeLeaving(domain.getAttendanceLeave().map(a -> TimeLeavingOfDailyPerformanceDto.getDto(a)));
			dto.setShortWorkTime(domain.getShortTime().map(s -> ShortTimeOfDailyDto.getDto(s)));
			dto.setSpecificDateAttr(domain.getSpecDateAttr().map(s -> SpecificDateAttrOfDailyPerforDto.getDto(s)));
			dto.setAttendanceLeavingGate(domain.getAttendanceLeavingGate().map(a -> AttendanceLeavingGateOfDailyDto.getDto(a)));
			dto.setOptionalItem(domain.getAnyItemValue().map(a -> OptionalItemOfDailyPerformDto.getDto(a)));
			dto.setEditStates(domain.getEditState().stream().map(c -> EditStateOfDailyPerformanceDto.getDto(c)).collect(Collectors.toList()));
			dto.setTemporaryTime(domain.getTempTime().map(t -> TemporaryTimeOfDailyPerformanceDto.getDto(t)));
//			this.setRemarks(domain.get)
			dto.exsistData();
		}
		return dto;
	}
	
	public static DailyRecordDto builder() {
		return new DailyRecordDto();
	}

	public DailyRecordDto withWorkInfo(WorkInformationOfDailyDto workInfo) {
		this.workInfo = workInfo;
		return this;
	}

	public DailyRecordDto withCalcAttr(CalcAttrOfDailyPerformanceDto calcAttr) {
		this.calcAttr = calcAttr;
		return this;
	}

	public DailyRecordDto withBusinessType(BusinessTypeOfDailyPerforDto businessType) {
		this.businessType = Optional.ofNullable(businessType);
		return this;
	}

	public DailyRecordDto withAffiliationInfo(AffiliationInforOfDailyPerforDto affiliationInfo) {
		this.affiliationInfo = affiliationInfo;
		return this;
	}
	
	public DailyRecordDto withErrors(EmployeeDailyPerErrorDto errors) {
		this.errors = errors;
		return this;
	}
	
	public DailyRecordDto outingTime(OutingTimeOfDailyPerformanceDto outingTime) {
		this.outingTime = Optional.ofNullable(outingTime);
		return this;
	}

	public DailyRecordDto addBreakTime(BreakTimeDailyDto breakTime) {
		this.breakTime.add(breakTime);
		return this;
	}

	public DailyRecordDto addBreakTime(List<BreakTimeDailyDto> breakTime) {
		if (breakTime == null) {
			return this;
		}
		this.breakTime.addAll(breakTime);
		return this;
	}

	public DailyRecordDto breakTime(List<BreakTimeDailyDto> breakTime) {
		this.breakTime = breakTime == null ? new ArrayList<>() : breakTime;
		return this;
	}

	public DailyRecordDto attendanceTime(AttendanceTimeDailyPerformDto attendanceTime) {
		this.attendanceTime = Optional.ofNullable(attendanceTime);
		return this;
	}

	public DailyRecordDto attendanceTimeByWork(AttendanceTimeByWorkOfDailyDto attendanceTimeByWork) {
		this.attendanceTimeByWork = Optional.ofNullable(attendanceTimeByWork);
		return this;
	}

	public DailyRecordDto timeLeaving(TimeLeavingOfDailyPerformanceDto timeLeaving) {
		this.timeLeaving = Optional.ofNullable(timeLeaving);
		return this;
	}

	public DailyRecordDto shortWorkTime(ShortTimeOfDailyDto shortWorkTime) {
		this.shortWorkTime = Optional.ofNullable(shortWorkTime);
		return this;
	}

	public DailyRecordDto specificDateAttr(SpecificDateAttrOfDailyPerforDto specificDateAttr) {
		this.specificDateAttr = Optional.ofNullable(specificDateAttr);
		return this;
	}

	public DailyRecordDto attendanceLeavingGate(AttendanceLeavingGateOfDailyDto attendanceLeavingGate) {
		this.attendanceLeavingGate = Optional.ofNullable(attendanceLeavingGate);
		return this;
	}

	public DailyRecordDto optionalItems(OptionalItemOfDailyPerformDto optionalItem) {
		this.optionalItem = Optional.ofNullable(optionalItem);
		return this;
	}

	public DailyRecordDto addEditStates(EditStateOfDailyPerformanceDto editStates) {
		if(this.editStates.stream().filter(c -> c.getAttendanceItemId() == editStates.getAttendanceItemId()).findFirst().isPresent()){
			return this;
		}
		this.editStates.add(editStates);
		return this;
	}

	public DailyRecordDto addEditStates(List<EditStateOfDailyPerformanceDto> editStates) {
		if (editStates == null) {
			return this;
		}
		List<Integer> current = this.editStates.stream().map(c -> c.getAttendanceItemId()).collect(Collectors.toList());
		this.editStates.addAll(editStates.stream().filter(c -> !current.contains(c.getAttendanceItemId())).collect(Collectors.toList()));
		return this;
	}

	public DailyRecordDto editStates(List<EditStateOfDailyPerformanceDto> editStates) {
		this.editStates = editStates == null ? new ArrayList<>() : editStates;
		return this;
	}

	public DailyRecordDto temporaryTime(TemporaryTimeOfDailyPerformanceDto temporaryTime) {
		this.temporaryTime = Optional.ofNullable(temporaryTime);
		return this;
	}
	public DailyRecordDto pcLogInfo(PCLogOnInforOfDailyPerformDto pcLogInfo) {
		this.pcLogInfo = Optional.ofNullable(pcLogInfo);
		return this;
	}
	
	public DailyRecordDto addRemarks(RemarksOfDailyDto remarks) {
		this.remarks.add(remarks);
		return this;
	}

	public DailyRecordDto addRemarks(List<RemarksOfDailyDto> remarks) {
		if (breakTime == null) {
			return this;
		}
		this.remarks.addAll(remarks);
		return this;
	}

	public DailyRecordDto remarks(List<RemarksOfDailyDto> remarks) {
		this.remarks = remarks == null ? new ArrayList<>() : remarks;
		return this;
	}

	public DailyRecordDto workingDate(GeneralDate workingDate) {
		this.date = workingDate;
		return this;
	}

	public DailyRecordDto employeeId(String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public DailyRecordDto complete() {
		return this;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}

	@Override
	public IntegrationOfDaily toDomain(String employeeId, GeneralDate date) {
		return new IntegrationOfDaily(
				this.workInfo == null ? null : this.workInfo.toDomain(employeeId, date), 
				this.calcAttr == null ? null : this.calcAttr.toDomain(employeeId, date), 
				this.affiliationInfo == null ? null : this.affiliationInfo.toDomain(employeeId, date),
				this.businessType.map(b -> b.toDomain(employeeId, date)),
				this.pcLogInfo.map(pc -> pc.toDomain(employeeId, date)),
				this.errors == null ? new ArrayList<>() : Arrays.asList(this.errors.toDomain(employeeId, date)),
				this.outingTime.map(ot -> ot.toDomain(employeeId, date)),
				this.breakTime.stream().map(bt -> bt.toDomain(employeeId, date)).collect(Collectors.toList()),
				this.attendanceTime.map(at -> at.toDomain(employeeId, date)),
				this.attendanceTimeByWork.map(atb -> atb.toDomain(employeeId, date)),
				this.timeLeaving.map(tl -> tl.toDomain(employeeId, date)),
				this.shortWorkTime.map(swt -> swt.toDomain(employeeId, date)),
				this.specificDateAttr.map(sda -> sda.toDomain(employeeId, date)),
				this.attendanceLeavingGate.map(alg -> alg.toDomain(employeeId, date)),
				this.optionalItem.map(oi -> oi.toDomain(employeeId, date)),
				this.editStates.stream().map(editS -> editS.toDomain(employeeId, date)).collect(Collectors.toList()),
				this.temporaryTime.map(tt -> tt.toDomain(employeeId, date))
				);
	}
}

