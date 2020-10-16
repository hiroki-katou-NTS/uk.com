package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
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
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConverterCommonService;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;

public class DailyRecordToAttendanceItemConverterImpl extends AttendanceItemConverterCommonService
				implements DailyRecordToAttendanceItemConverter {

	private String employeeId;
	private GeneralDate ymd;
	
	private List<EmployeeDailyPerError> errors;
	private List<EditStateOfDailyAttd> editStates;

	private DailyRecordToAttendanceItemConverterImpl(Map<Integer, OptionalItem> optionalItems,
			OptionalItemRepository optionalItem) {
		
		super(optionalItems, optionalItem);
	}
	
	@Override
	public IntegrationOfDaily toDomain() {
		return new IntegrationOfDaily(workInfo(), calcAttr(), affiliationInfo(), pcLogInfo(),
				this.errors, outingTime(), breakTime(), attendanceTime(), timeLeaving(),
				shortTime(), specificDateAttr(), attendanceLeavingGate(), anyItems(), editStates(), temporaryTime(),
				remarks());
	}

	@Override
	public DailyRecordToAttendanceItemConverter setData(IntegrationOfDaily domain) {

		this.employeeId(domain.getEmployeeId());
		this.workingDate(domain.getYmd());
		this.withWorkInfo(domain.getWorkInformation());
		this.withCalcAttr(domain.getCalAttr());
		this.withAffiliationInfo(domain.getAffiliationInfor());
//		this.withBusinessType(domain.getBusinessType().orElse(null));
		if (domain.getEmployeeError() != null && !domain.getEmployeeError().isEmpty()) {
			this.withEmployeeErrors(domain.getEmployeeError());
		}
		this.withOutingTime(domain.getOutingTime().orElse(null));
		this.withBreakTime(domain.getBreakTime());
		this.withAttendanceTime(domain.getAttendanceTimeOfDailyPerformance().orElse(null));
//		this.withAttendanceTimeByWork(domain.getAttendancetimeByWork().orElse(null));
		this.withTimeLeaving(domain.getAttendanceLeave().orElse(null));
		this.withShortTime(domain.getShortTime().orElse(null));
		this.withSpecificDateAttr(domain.getSpecDateAttr().orElse(null));
		this.withAttendanceLeavingGate(domain.getAttendanceLeavingGate().orElse(null));
		this.withAnyItems(domain.getAnyItemValue().orElse(null));
		this.withEditStates(domain.getEditState());
		this.withTemporaryTime(domain.getTempTime().orElse(null));
		this.withPCLogInfo(domain.getPcLogOnInfo().orElse(null));
		this.withRemarks(domain.getRemarks());
		return this;
	}

	public static DailyRecordToAttendanceItemConverter builder(OptionalItemRepository optionalItem) {

		return new DailyRecordToAttendanceItemConverterImpl(new HashMap<>(), optionalItem);
	}

	public static DailyRecordToAttendanceItemConverter builder(Map<Integer, OptionalItem> optionalItems,
			OptionalItemRepository optionalItem) {

		return new DailyRecordToAttendanceItemConverterImpl(optionalItems, optionalItem);
	}

	public DailyRecordToAttendanceItemConverter withWorkInfo(WorkInfoOfDailyAttendance domain) {

		this.domainSource.put(ItemConst.DAILY_WORK_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_WORK_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_WORK_INFO_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withCalcAttr(CalAttrOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_CALCULATION_ATTR_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_CALCULATION_ATTR_NAME, null);
		this.itemValues.put(ItemConst.DAILY_CALCULATION_ATTR_NAME, null);
		return this;
	}

//	public DailyRecordToAttendanceItemConverter withBusinessType(WorkTypeOfDailyPerformance domain) {
//		this.dailyRecord.withBusinessType(BusinessTypeOfDailyPerforDto.getDto(domain));
//		return this;
//	}

	public DailyRecordToAttendanceItemConverter withAffiliationInfo(AffiliationInforOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_AFFILIATION_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_AFFILIATION_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_AFFILIATION_INFO_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEmployeeErrors(List<EmployeeDailyPerError> domain) {

		this.errors = domain;
		return this;
	}

	public DailyRecordToAttendanceItemConverter withOutingTime(OutingTimeOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_OUTING_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_OUTING_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_OUTING_TIME_NAME, null);
		return this;
	}
	
	public DailyRecordToAttendanceItemConverter withBreakTime(List<BreakTimeOfDailyAttd> domain) {

		this.dtoSource.put(ItemConst.DAILY_BREAK_TIME_NAME, domain);
		this.itemValues.put(ItemConst.DAILY_BREAK_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfDailyAttendance domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDANCE_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDANCE_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDANCE_TIME_NAME, null);
		return this;
	}

//ichiokaDEL
//	public DailyRecordToAttendanceItemConverter withAttendanceTimeByWork(AttendanceTimeByWorkOfDaily domain) {
//
//		this.domainSource.put(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME, domain);
//		this.dtoSource.put(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME, null);
//		this.itemValues.put(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME, null);
//		return this;
//	}

	public DailyRecordToAttendanceItemConverter withTimeLeaving(TimeLeavingOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDACE_LEAVE_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDACE_LEAVE_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDACE_LEAVE_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withShortTime(ShortTimeOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_SHORT_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_SHORT_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_SHORT_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withSpecificDateAttr(SpecificDateAttrOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME, null);
		this.itemValues.put(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceLeavingGate(AttendanceLeavingGateOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAnyItems(AnyItemValueOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_OPTIONAL_ITEM_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_OPTIONAL_ITEM_NAME, null);
		this.itemValues.put(ItemConst.DAILY_OPTIONAL_ITEM_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEditStates(EditStateOfDailyAttd domain) {

		if (this.editStates == null) {
			this.editStates = new ArrayList<>();
			this.editStates.add(domain);
		} else {
			this.editStates.removeIf(e -> e.getAttendanceItemId() == domain.getAttendanceItemId());
			this.editStates.add(domain);
		}
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEditStates(List<EditStateOfDailyAttd> domain) {

		this.editStates = new ArrayList<>(domain);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withTemporaryTime(TemporaryTimeOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_TEMPORARY_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_TEMPORARY_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_TEMPORARY_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withPCLogInfo(PCLogOnInfoOfDailyAttd domain) {

		this.domainSource.put(ItemConst.DAILY_PC_LOG_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_PC_LOG_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_PC_LOG_INFO_NAME, null);
		return this;
	}

	@Override
	public DailyRecordToAttendanceItemConverter withRemarks(List<RemarksOfDailyAttd> domain) {

		this.dtoSource.put(ItemConst.DAILY_REMARKS_NAME, domain);
		this.itemValues.put(ItemConst.DAILY_REMARKS_NAME, null);

		return this;
	}

	public DailyRecordToAttendanceItemConverter employeeId(String employeeId) {
		this.employeeId = (employeeId);
		return this;
	}

	public DailyRecordToAttendanceItemConverter workingDate(GeneralDate workingDate) {
		this.ymd = (workingDate);
		return this;
	}

	public DailyRecordToAttendanceItemConverter completed() {
		return this;
	}

	@Override
	public WorkInfoOfDailyAttendance workInfo() {

		return (WorkInfoOfDailyAttendance) getDomain(ItemConst.DAILY_WORK_INFO_NAME);
	}
	
	@Override
	public CalAttrOfDailyAttd calcAttr() {

		return (CalAttrOfDailyAttd) getDomain(ItemConst.DAILY_CALCULATION_ATTR_NAME);
	}

//ichiokaDEL
//	@Override
//	public Optional<WorkTypeOfDailyPerformance> businessType() {
//
//		return Optional.ofNullable((WorkTypeOfDailyPerformance) getDomain(ItemConst.DAILY_BUSINESS_TYPE_NAME));
//	}

	@Override
	public AffiliationInforOfDailyAttd affiliationInfo() {

		return (AffiliationInforOfDailyAttd) getDomain(ItemConst.DAILY_AFFILIATION_INFO_NAME);
	}

	@Override
	public Optional<OutingTimeOfDailyAttd> outingTime() {

		return Optional.ofNullable((OutingTimeOfDailyAttd) getDomain(ItemConst.DAILY_OUTING_TIME_NAME));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BreakTimeOfDailyAttd> breakTime() {

		return (List<BreakTimeOfDailyAttd>) getDomains(ItemConst.DAILY_BREAK_TIME_NAME);
	}

	@Override
	public Optional<AttendanceTimeOfDailyAttendance> attendanceTime() {

		return Optional.ofNullable((AttendanceTimeOfDailyAttendance) getDomain(ItemConst.DAILY_ATTENDANCE_TIME_NAME));
	}

//ichiokaDEL
//	@Override
//	public Optional<AttendanceTimeByWorkOfDaily> attendanceTimeByWork() {
//
//		return Optional
//				.ofNullable((AttendanceTimeByWorkOfDaily) getDomain(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME));
//	}

	@Override
	public Optional<TimeLeavingOfDailyAttd> timeLeaving() {

		return Optional.ofNullable((TimeLeavingOfDailyAttd) getDomain(ItemConst.DAILY_ATTENDACE_LEAVE_NAME));
	}

	@Override
	public Optional<ShortTimeOfDailyAttd> shortTime() {

		return Optional.ofNullable((ShortTimeOfDailyAttd) getDomain(ItemConst.DAILY_SHORT_TIME_NAME));
	}

	@Override
	public Optional<SpecificDateAttrOfDailyAttd> specificDateAttr() {

		return Optional.ofNullable((SpecificDateAttrOfDailyAttd) getDomain(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME));
	}

	@Override
	public Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate() {

		return Optional.ofNullable((AttendanceLeavingGateOfDailyAttd) getDomain(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME));
	}

	@Override
	public Optional<AnyItemValueOfDailyAttd> anyItems() {

		return Optional.ofNullable((AnyItemValueOfDailyAttd) getDomain(ItemConst.DAILY_OPTIONAL_ITEM_NAME));
	}

	@Override
	public List<EditStateOfDailyAttd> editStates() {

		return this.editStates;
	}

	@Override
	public Optional<TemporaryTimeOfDailyAttd> temporaryTime() {

		return Optional.ofNullable((TemporaryTimeOfDailyAttd) getDomain(ItemConst.DAILY_TEMPORARY_TIME_NAME));
	}

	@Override
	public Optional<PCLogOnInfoOfDailyAttd> pcLogInfo() {

		return Optional.ofNullable((PCLogOnInfoOfDailyAttd) getDomain(ItemConst.DAILY_PC_LOG_INFO_NAME));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemarksOfDailyAttd> remarks() {

		return (List<RemarksOfDailyAttd>) getDomains(ItemConst.DAILY_REMARKS_NAME);
	}

	@Override
	protected boolean isOpyionalItem(String type) {
		return type.equals(ItemConst.DAILY_OPTIONAL_ITEM_NAME);
	}

	@Override
	protected Object correctOptionalItem(Object dto) {
		
		if (dto == null) {
			return dto;
		}
		
		OptionalItemOfDailyPerformDto optional = (OptionalItemOfDailyPerformDto) dto;
		
		optional.correctItems(loadOptionalItemMaster());
		
		return optional.toDomain(employeeId, ymd);
	}

	@Override
	protected Object toDomain(ConvertibleAttendanceItem dto){
		
		return dto.toDomain(employeeId, ymd);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void convertDomainToDto(String type) {
		
		switch (type) {
		case ItemConst.DAILY_WORK_INFO_NAME:
			processOnDomain(type, c -> WorkInformationOfDailyDto.getDto(this.employeeId, this.ymd, (WorkInfoOfDailyAttendance) c));
			break;
		case ItemConst.DAILY_AFFILIATION_INFO_NAME:
			processOnDomain(type, c -> AffiliationInforOfDailyPerforDto.getDto(this.employeeId, this.ymd, (AffiliationInforOfDailyAttd) c));
			break;
		case ItemConst.DAILY_CALCULATION_ATTR_NAME:
			processOnDomain(type, c -> CalcAttrOfDailyPerformanceDto.getDto(this.employeeId, this.ymd, (CalAttrOfDailyAttd) c));
			break;
		case ItemConst.DAILY_BUSINESS_TYPE_NAME:
			processOnDomain(type, c -> BusinessTypeOfDailyPerforDto.getDto((WorkTypeOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_OUTING_TIME_NAME:
			processOnDomain(type, c -> OutingTimeOfDailyPerformanceDto.getDto(this.employeeId, this.ymd, (OutingTimeOfDailyAttd) c));
			break;
		case ItemConst.DAILY_BREAK_TIME_NAME:
			processOnDomain(type, c -> BreakTimeDailyDto.getDto(this.employeeId, this.ymd, (List<BreakTimeOfDailyAttd>) c));
			break;
		case ItemConst.DAILY_ATTENDANCE_TIME_NAME:
			processOnDomain(type, c -> AttendanceTimeDailyPerformDto.getDto(this.employeeId, this.ymd, (AttendanceTimeOfDailyAttendance) c));
			break;
		case ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME:
			processOnDomain(type, c -> AttendanceTimeByWorkOfDailyDto.getDto((AttendanceTimeByWorkOfDaily) c));
			break;
		case ItemConst.DAILY_ATTENDACE_LEAVE_NAME:
			processOnDomain(type, c -> TimeLeavingOfDailyPerformanceDto.getDto(this.employeeId, this.ymd, (TimeLeavingOfDailyAttd) c));
			break;
		case ItemConst.DAILY_SHORT_TIME_NAME:
			processOnDomain(type, c -> ShortTimeOfDailyDto.getDto(this.employeeId, this.ymd, (ShortTimeOfDailyAttd) c));
			break;
		case ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME:
			processOnDomain(type, c -> SpecificDateAttrOfDailyPerforDto.getDto(this.employeeId, this.ymd, (SpecificDateAttrOfDailyAttd) c));
			break;
		case ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME:
			processOnDomain(type, c -> AttendanceLeavingGateOfDailyDto.getDto(this.employeeId, this.ymd, (AttendanceLeavingGateOfDailyAttd) c));
			break;
		case ItemConst.DAILY_OPTIONAL_ITEM_NAME:
			processOnDomain(type, c -> OptionalItemOfDailyPerformDto.getDto(this.employeeId, this.ymd, (AnyItemValueOfDailyAttd) c, loadOptionalItemMaster()));
			break;
		case ItemConst.DAILY_TEMPORARY_TIME_NAME:
			processOnDomain(type, c -> TemporaryTimeOfDailyPerformanceDto.getDto(this.employeeId, this.ymd, (TemporaryTimeOfDailyAttd) c));
			break;
		case ItemConst.DAILY_PC_LOG_INFO_NAME:
			processOnDomain(type, c -> PCLogOnInforOfDailyPerformDto.from(this.employeeId, this.ymd, (PCLogOnInfoOfDailyAttd) c));
			break;
		case ItemConst.DAILY_REMARKS_NAME:
			processOnDomain(type, c -> RemarksOfDailyDto.getDto(this.employeeId, this.ymd, (List<RemarksOfDailyAttd>) c));
			break;
		default:
			break;
		}
	}

	@Override
	protected boolean isMonthly() {
		return false;
	}
}
