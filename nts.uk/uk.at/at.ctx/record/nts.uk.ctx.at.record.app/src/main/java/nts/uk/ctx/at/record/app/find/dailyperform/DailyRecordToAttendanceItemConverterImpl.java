package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
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
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
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
import nts.uk.shr.com.context.AppContexts;

public class DailyRecordToAttendanceItemConverterImpl extends AttendanceItemConverterCommonService
				implements DailyRecordToAttendanceItemConverter {

	private String employeeId;
	private GeneralDate ymd;
	
	private List<EmployeeDailyPerError> errors;
	private List<EditStateOfDailyPerformance> editStates;

	private DailyRecordToAttendanceItemConverterImpl(Map<Integer, OptionalItem> optionalItems,
			OptionalItemRepository optionalItem) {
		
		super(optionalItems, optionalItem);
	}
	
	@Override
	public IntegrationOfDaily toDomain() {
		return new IntegrationOfDaily(workInfo(), calcAttr(), affiliationInfo(), businessType(), pcLogInfo(),
				this.errors, outingTime(), breakTime(), attendanceTime(), attendanceTimeByWork(), timeLeaving(),
				shortTime(), specificDateAttr(), attendanceLeavingGate(), anyItems(), editStates(), temporaryTime(),
				remarks());
	}

	@Override
	public DailyRecordToAttendanceItemConverter setData(IntegrationOfDaily domain) {

		this.employeeId(domain.getAffiliationInfor().getEmployeeId());
		this.workingDate(domain.getAffiliationInfor().getYmd());
		this.withWorkInfo(domain.getWorkInformation());
		this.withCalcAttr(domain.getCalAttr());
		this.withAffiliationInfo(domain.getAffiliationInfor());
		this.withBusinessType(domain.getBusinessType().orElse(null));
		if (domain.getEmployeeError() != null && !domain.getEmployeeError().isEmpty()) {
			this.withEmployeeErrors(domain.getEmployeeError());
		}
		this.withOutingTime(domain.getOutingTime().orElse(null));
		this.withBreakTime(domain.getBreakTime());
		this.withAttendanceTime(domain.getAttendanceTimeOfDailyPerformance().orElse(null));
		this.withAttendanceTimeByWork(domain.getAttendancetimeByWork().orElse(null));
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

	public DailyRecordToAttendanceItemConverter withWorkInfo(WorkInfoOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_WORK_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_WORK_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_WORK_INFO_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withCalcAttr(CalAttrOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_CALCULATION_ATTR_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_CALCULATION_ATTR_NAME, null);
		this.itemValues.put(ItemConst.DAILY_CALCULATION_ATTR_NAME, null);
		return this;
	}

//	public DailyRecordToAttendanceItemConverter withBusinessType(WorkTypeOfDailyPerformance domain) {
//		this.dailyRecord.withBusinessType(BusinessTypeOfDailyPerforDto.getDto(domain));
//		return this;
//	}

	public DailyRecordToAttendanceItemConverter withAffiliationInfo(AffiliationInforOfDailyPerfor domain) {

		this.domainSource.put(ItemConst.DAILY_AFFILIATION_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_AFFILIATION_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_AFFILIATION_INFO_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEmployeeErrors(List<EmployeeDailyPerError> domain) {

		this.errors = domain;
		return this;
	}

	public DailyRecordToAttendanceItemConverter withOutingTime(OutingTimeOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_OUTING_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_OUTING_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_OUTING_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withBreakTime(List<BreakTimeOfDailyPerformance> domain) {

		this.dtoSource.put(ItemConst.DAILY_BREAK_TIME_NAME, BreakTimeDailyDto.getDto(domain));
		this.itemValues.put(ItemConst.DAILY_BREAK_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDANCE_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDANCE_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDANCE_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceTimeByWork(AttendanceTimeByWorkOfDaily domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withTimeLeaving(TimeLeavingOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDACE_LEAVE_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDACE_LEAVE_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDACE_LEAVE_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withShortTime(ShortTimeOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_SHORT_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_SHORT_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_SHORT_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withSpecificDateAttr(SpecificDateAttrOfDailyPerfor domain) {

		this.domainSource.put(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME, null);
		this.itemValues.put(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceLeavingGate(AttendanceLeavingGateOfDaily domain) {

		this.domainSource.put(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME, null);
		this.itemValues.put(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAnyItems(AnyItemValueOfDaily domain) {

		this.domainSource.put(ItemConst.DAILY_OPTIONAL_ITEM_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_OPTIONAL_ITEM_NAME, null);
		this.itemValues.put(ItemConst.DAILY_OPTIONAL_ITEM_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEditStates(EditStateOfDailyPerformance domain) {

		if (this.editStates == null) {
			this.editStates = new ArrayList<>();
			this.editStates.add(domain);
		} else {
			this.editStates.removeIf(e -> e.getAttendanceItemId() == domain.getAttendanceItemId());
			this.editStates.add(domain);
		}
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEditStates(List<EditStateOfDailyPerformance> domain) {

		this.editStates = new ArrayList<>(domain);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withTemporaryTime(TemporaryTimeOfDailyPerformance domain) {

		this.domainSource.put(ItemConst.DAILY_TEMPORARY_TIME_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_TEMPORARY_TIME_NAME, null);
		this.itemValues.put(ItemConst.DAILY_TEMPORARY_TIME_NAME, null);
		return this;
	}

	public DailyRecordToAttendanceItemConverter withPCLogInfo(PCLogOnInfoOfDaily domain) {

		this.domainSource.put(ItemConst.DAILY_PC_LOG_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_PC_LOG_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_PC_LOG_INFO_NAME, null);
		return this;
	}

	@Override
	public DailyRecordToAttendanceItemConverter withRemark(RemarksOfDailyPerform domain) {
		this.dailyRecord.addRemarks(RemarksOfDailyDto.getDto(domain));
		this.domainSource.put(ItemConst.DAILY_PC_LOG_INFO_NAME, domain);
		this.dtoSource.put(ItemConst.DAILY_PC_LOG_INFO_NAME, null);
		this.itemValues.put(ItemConst.DAILY_PC_LOG_INFO_NAME, null);
		return this;
	}

	@Override
	public DailyRecordToAttendanceItemConverter withRemarks(List<RemarksOfDailyPerform> domain) {

		this.dtoSource.put(ItemConst.DAILY_REMARKS_NAME, RemarksOfDailyDto.getDto(domain));
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
	public WorkInfoOfDailyPerformance workInfo() {

		return (WorkInfoOfDailyPerformance) getDomain(ItemConst.DAILY_WORK_INFO_NAME);
	}

	@Override
	public CalAttrOfDailyPerformance calcAttr() {

		return (CalAttrOfDailyPerformance) getDomain(ItemConst.DAILY_CALCULATION_ATTR_NAME);
	}

	@Override
	public Optional<WorkTypeOfDailyPerformance> businessType() {

		return Optional.ofNullable((WorkTypeOfDailyPerformance) getDomain(ItemConst.DAILY_BUSINESS_TYPE_NAME));
	}

	@Override
	public AffiliationInforOfDailyPerfor affiliationInfo() {

		return (AffiliationInforOfDailyPerfor) getDomain(ItemConst.DAILY_AFFILIATION_INFO_NAME);
	}

	@Override
	public Optional<OutingTimeOfDailyPerformance> outingTime() {

		return Optional.ofNullable((OutingTimeOfDailyPerformance) getDomain(ItemConst.DAILY_OUTING_TIME_NAME));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BreakTimeOfDailyPerformance> breakTime() {

		return (List<BreakTimeOfDailyPerformance>) getDomains(ItemConst.DAILY_BREAK_TIME_NAME);
	}

	@Override
	public Optional<AttendanceTimeOfDailyPerformance> attendanceTime() {

		return Optional.ofNullable((AttendanceTimeOfDailyPerformance) getDomain(ItemConst.DAILY_ATTENDANCE_TIME_NAME));
	}

	@Override
	public Optional<AttendanceTimeByWorkOfDaily> attendanceTimeByWork() {

		return Optional
				.ofNullable((AttendanceTimeByWorkOfDaily) getDomain(ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME));
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> timeLeaving() {

		return Optional.ofNullable((TimeLeavingOfDailyPerformance) getDomain(ItemConst.DAILY_ATTENDACE_LEAVE_NAME));
	}

	@Override
	public Optional<ShortTimeOfDailyPerformance> shortTime() {

		return Optional.ofNullable((ShortTimeOfDailyPerformance) getDomain(ItemConst.DAILY_SHORT_TIME_NAME));
	}

	@Override
	public Optional<SpecificDateAttrOfDailyPerfor> specificDateAttr() {

		return Optional.ofNullable((SpecificDateAttrOfDailyPerfor) getDomain(ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME));
	}

	@Override
	public Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate() {

		return Optional.ofNullable((AttendanceLeavingGateOfDaily) getDomain(ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME));
	}

	@Override
	public Optional<AnyItemValueOfDaily> anyItems() {

		return Optional.ofNullable((AnyItemValueOfDaily) getDomain(ItemConst.DAILY_OPTIONAL_ITEM_NAME));
	}

	@Override
	public List<EditStateOfDailyPerformance> editStates() {

		return this.editStates;
	}

	@Override
	public Optional<TemporaryTimeOfDailyPerformance> temporaryTime() {

		return Optional.ofNullable((TemporaryTimeOfDailyPerformance) getDomain(ItemConst.DAILY_TEMPORARY_TIME_NAME));
	}

	@Override
	public Optional<PCLogOnInfoOfDaily> pcLogInfo() {

		return Optional.ofNullable((PCLogOnInfoOfDaily) getDomain(ItemConst.DAILY_PC_LOG_INFO_NAME));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemarksOfDailyPerform> remarks() {

		return (List<RemarksOfDailyPerform>) getDomains(ItemConst.DAILY_REMARKS_NAME);
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
			processOnDomain(type, c -> WorkInformationOfDailyDto.getDto((WorkInfoOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_AFFILIATION_INFO_NAME:
			processOnDomain(type, c -> AffiliationInforOfDailyPerforDto.getDto((AffiliationInforOfDailyPerfor) c));
			break;
		case ItemConst.DAILY_CALCULATION_ATTR_NAME:
			processOnDomain(type, c -> CalcAttrOfDailyPerformanceDto.getDto((CalAttrOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_BUSINESS_TYPE_NAME:
			processOnDomain(type, c -> BusinessTypeOfDailyPerforDto.getDto((WorkTypeOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_OUTING_TIME_NAME:
			processOnDomain(type, c -> OutingTimeOfDailyPerformanceDto.getDto((OutingTimeOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_BREAK_TIME_NAME:
			processOnDomain(type, c -> BreakTimeDailyDto.getDto((List<BreakTimeOfDailyPerformance>) c));
			break;
		case ItemConst.DAILY_ATTENDANCE_TIME_NAME:
			processOnDomain(type, c -> AttendanceTimeDailyPerformDto.getDto((AttendanceTimeOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_ATTENDANCE_TIME_BY_WORK_NAME:
			processOnDomain(type, c -> AttendanceTimeByWorkOfDailyDto.getDto((AttendanceTimeByWorkOfDaily) c));
			break;
		case ItemConst.DAILY_ATTENDACE_LEAVE_NAME:
			processOnDomain(type, c -> TimeLeavingOfDailyPerformanceDto.getDto((TimeLeavingOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_SHORT_TIME_NAME:
			processOnDomain(type, c -> ShortTimeOfDailyDto.getDto((ShortTimeOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_SPECIFIC_DATE_ATTR_NAME:
			processOnDomain(type, c -> SpecificDateAttrOfDailyPerforDto.getDto((SpecificDateAttrOfDailyPerfor) c));
			break;
		case ItemConst.DAILY_ATTENDANCE_LEAVE_GATE_NAME:
			processOnDomain(type, c -> AttendanceLeavingGateOfDailyDto.getDto((AttendanceLeavingGateOfDaily) c));
			break;
		case ItemConst.DAILY_OPTIONAL_ITEM_NAME:
			processOnDomain(type, c -> OptionalItemOfDailyPerformDto.getDto((AnyItemValueOfDaily) c, loadOptionalItemMaster()));
			break;
		case ItemConst.DAILY_TEMPORARY_TIME_NAME:
			processOnDomain(type, c -> TemporaryTimeOfDailyPerformanceDto.getDto((TemporaryTimeOfDailyPerformance) c));
			break;
		case ItemConst.DAILY_PC_LOG_INFO_NAME:
			processOnDomain(type, c -> PCLogOnInforOfDailyPerformDto.from((PCLogOnInfoOfDaily) c));
			break;
		case ItemConst.DAILY_REMARKS_NAME:
			processOnDomain(type, c -> RemarksOfDailyDto.getDto((List<RemarksOfDailyPerform>) c));
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
