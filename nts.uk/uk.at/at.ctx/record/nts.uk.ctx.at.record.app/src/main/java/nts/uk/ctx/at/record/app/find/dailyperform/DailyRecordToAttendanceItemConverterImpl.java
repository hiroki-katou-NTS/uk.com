package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
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

//@Stateless
public class DailyRecordToAttendanceItemConverterImpl implements DailyRecordToAttendanceItemConverter {

	private final DailyRecordDto dailyRecord;
	
	private final Map<Integer, OptionalItem> optionalItems;
	
	private DailyRecordToAttendanceItemConverterImpl(Map<Integer, OptionalItem> optionalItems) {
		this.dailyRecord = new DailyRecordDto();
		this.optionalItems = optionalItems;
	}

	@Override
	public Optional<ItemValue> convert(int attendanceItemId) {
		List<ItemValue> items = AttendanceItemUtil.toItemValues(this.dailyRecord, Arrays.asList(attendanceItemId));
		return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
	}

	@Override
	public List<ItemValue> convert(Collection<Integer> attendanceItemIds) {
		return AttendanceItemUtil.toItemValues(this.dailyRecord, attendanceItemIds);
	}

	
	@Override
	public void merge(ItemValue value) {
		AttendanceItemUtil.fromItemValues(dailyRecord, Arrays.asList(value));
	}

	@Override
	public void merge(Collection<ItemValue> values) {
		AttendanceItemUtil.fromItemValues(dailyRecord, values);
	}

	@Override
	public IntegrationOfDaily toDomain() {
		return (IntegrationOfDaily) dailyRecord.toDomain();
	}

	@Override
	public DailyRecordToAttendanceItemConverter setData(IntegrationOfDaily domain) {
		String employeeId = domain.getEmployeeId();
		GeneralDate ymd = domain.getYmd();
		this.employeeId(employeeId);
		this.workingDate(ymd);
		this.withWorkInfo(employeeId,ymd,domain.getWorkInformation());
		this.withCalcAttr(employeeId,ymd,domain.getCalAttr());
		this.withAffiliationInfo(employeeId,ymd,domain.getAffiliationInfor());
//		this.withBusinessType(domain.getBusinessType().orElse(null));
		if(domain.getEmployeeError() != null && !domain.getEmployeeError().isEmpty()) {
			this.withEmployeeErrors(domain.getEmployeeError());
		}
		this.withOutingTime(employeeId,ymd,domain.getOutingTime().orElse(null));
		this.withBreakTime(employeeId,ymd,domain.getBreakTime());
		this.withAttendanceTime(employeeId,ymd,domain.getAttendanceTimeOfDailyPerformance().orElse(null));
//		this.withAttendanceTimeByWork(domain.getAttendancetimeByWork().orElse(null));
		this.withTimeLeaving(employeeId,ymd,domain.getAttendanceLeave().orElse(null));
		this.withShortTime(employeeId,ymd,domain.getShortTime().orElse(null));
		this.withSpecificDateAttr(employeeId,ymd,domain.getSpecDateAttr().orElse(null));
		this.withAttendanceLeavingGate(employeeId,ymd,domain.getAttendanceLeavingGate().orElse(null));
		this.withAnyItems(employeeId,ymd,domain.getAnyItemValue().orElse(null));
		this.withEditStates(employeeId,ymd,domain.getEditState());
		this.withTemporaryTime(employeeId,ymd,domain.getTempTime().orElse(null));
		this.withPCLogInfo(employeeId,ymd,domain.getPcLogOnInfo().orElse(null));
		this.withRemarks(employeeId,ymd,domain.getRemarks());
		return this;
	}
	
	public static DailyRecordToAttendanceItemConverter builder(OptionalItemRepository optionalItem) {
		String compId = AppContexts.user().companyId();
		return new DailyRecordToAttendanceItemConverterImpl(
				optionalItem.findAll(compId).stream()
					.collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c)));
	}
	
	public static DailyRecordToAttendanceItemConverter builder(Map<Integer, OptionalItem> optionalItems) {
		return new DailyRecordToAttendanceItemConverterImpl(optionalItems);
	}

	public DailyRecordToAttendanceItemConverter withWorkInfo(String employeeId,GeneralDate ymd, WorkInfoOfDailyAttendance domain) {
		this.dailyRecord.withWorkInfo(WorkInformationOfDailyDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withCalcAttr(String employeeId,GeneralDate ymd,CalAttrOfDailyAttd domain) {
		this.dailyRecord.withCalcAttr(CalcAttrOfDailyPerformanceDto.getDto(employeeId,ymd,domain));
		return this;
	}

//	public DailyRecordToAttendanceItemConverter withBusinessType(WorkTypeOfDailyPerformance domain) {
//		this.dailyRecord.withBusinessType(BusinessTypeOfDailyPerforDto.getDto(domain));
//		return this;
//	}

	public DailyRecordToAttendanceItemConverter withAffiliationInfo(String employeeId,GeneralDate ymd,AffiliationInforOfDailyAttd domain) {
		this.dailyRecord.withAffiliationInfo(AffiliationInforOfDailyPerforDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEmployeeErrors(List<EmployeeDailyPerError> domain) {
		this.dailyRecord.withErrors(domain.stream().map(x -> EmployeeDailyPerErrorDto.getDto(x)).collect(Collectors.toList()));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withOutingTime(String employeeId,GeneralDate ymd,OutingTimeOfDailyAttd domain) {
		this.dailyRecord.outingTime(OutingTimeOfDailyPerformanceDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withBreakTime(String employeeId,GeneralDate ymd,BreakTimeOfDailyAttd domain) {
		this.dailyRecord.addBreakTime(BreakTimeDailyDto.getDto(employeeId,ymd,domain));
		return this;
	}
	
	public DailyRecordToAttendanceItemConverter withBreakTime(String employeeId,GeneralDate ymd,List<BreakTimeOfDailyAttd> domain) {
		this.dailyRecord.breakTime(domain.stream().map(c -> BreakTimeDailyDto.getDto(employeeId,ymd,c)).collect(Collectors.toList()));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceTime(String employeeId,GeneralDate ymd,AttendanceTimeOfDailyAttendance domain) {
		this.dailyRecord.attendanceTime(AttendanceTimeDailyPerformDto.getDto(employeeId,ymd,domain));
		return this;
	}

//	public DailyRecordToAttendanceItemConverter withAttendanceTimeByWork(AttendanceTimeByWorkOfDaily domain) {
//		this.dailyRecord.attendanceTimeByWork(AttendanceTimeByWorkOfDailyDto.getDto(domain));
//		return this;
//	}

	public DailyRecordToAttendanceItemConverter withTimeLeaving(String employeeId,GeneralDate ymd,TimeLeavingOfDailyAttd domain) {
		this.dailyRecord.timeLeaving(TimeLeavingOfDailyPerformanceDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withShortTime(String employeeId,GeneralDate ymd,ShortTimeOfDailyAttd domain) {
		this.dailyRecord.shortWorkTime(ShortTimeOfDailyDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withSpecificDateAttr(String employeeId,GeneralDate ymd,SpecificDateAttrOfDailyAttd domain) {
		this.dailyRecord.specificDateAttr(SpecificDateAttrOfDailyPerforDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAttendanceLeavingGate(String employeeId,GeneralDate ymd,AttendanceLeavingGateOfDailyAttd domain) {
		this.dailyRecord.attendanceLeavingGate(AttendanceLeavingGateOfDailyDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withAnyItems(String employeeId,GeneralDate ymd,AnyItemValueOfDailyAttd domain) {
		this.dailyRecord.optionalItems(OptionalItemOfDailyPerformDto.getDto(employeeId,ymd,domain, this.optionalItems));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEditStates(String employeeId,GeneralDate ymd,EditStateOfDailyAttd domain) {
		this.dailyRecord.addEditStates(EditStateOfDailyPerformanceDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withEditStates(String employeeId,GeneralDate ymd,List<EditStateOfDailyAttd> domain) {
//		List<Integer> current = this.dailyRecord.getEditStates().stream().map(c -> c.getAttendanceItemId()).collect(Collectors.toList());
		this.dailyRecord.editStates(domain.stream().map(c -> EditStateOfDailyPerformanceDto.getDto(employeeId,ymd,c)).collect(Collectors.toList()));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withTemporaryTime(String employeeId,GeneralDate ymd,TemporaryTimeOfDailyAttd domain) {
		this.dailyRecord.temporaryTime(TemporaryTimeOfDailyPerformanceDto.getDto(employeeId,ymd,domain));
		return this;
	}

	public DailyRecordToAttendanceItemConverter withPCLogInfo(String employeeId,GeneralDate ymd,PCLogOnInfoOfDailyAttd domain) {
		this.dailyRecord.pcLogInfo(PCLogOnInforOfDailyPerformDto.from(employeeId,ymd,domain));
		return this;
	}

	@Override
	public DailyRecordToAttendanceItemConverter withRemark(String employeeId,GeneralDate ymd,RemarksOfDailyAttd domain) {
		this.dailyRecord.addRemarks(RemarksOfDailyDto.getDto(employeeId,ymd,domain));
		return this;
	}

	@Override
	public DailyRecordToAttendanceItemConverter withRemarks(String employeeId,GeneralDate ymd,List<RemarksOfDailyAttd> domain) {
		this.dailyRecord.remarks(domain.stream().map(c -> RemarksOfDailyDto.getDto(employeeId,ymd,c)).collect(Collectors.toList()));
		return this;
	}
	
	public DailyRecordToAttendanceItemConverter employeeId(String employeeId) {
		this.dailyRecord.employeeId(employeeId);
		return this;
	}

	public DailyRecordToAttendanceItemConverter workingDate(GeneralDate workingDate) {
		this.dailyRecord.workingDate(workingDate);
		return this;
	}


	public DailyRecordToAttendanceItemConverter completed(){
		return this;
	}

	@Override
	public WorkInfoOfDailyAttendance workInfo() {
		return this.dailyRecord.getWorkInfo().toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate());
	}

	@Override
	public CalAttrOfDailyAttd calcAttr() {
		return this.dailyRecord.getCalcAttr().toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate());
	}

//	@Override
//	public Optional<WorkTypeOfDailyPerformance> businessType() {
//		return this.dailyRecord.getBusinessType().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
//	}

	@Override
	public AffiliationInforOfDailyAttd affiliationInfo() {
		return this.dailyRecord.getAffiliationInfo().toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate());
	}

	@Override
	public Optional<OutingTimeOfDailyAttd> outingTime() {
		return this.dailyRecord.getOutingTime().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public List<BreakTimeOfDailyAttd> breakTime() {
		return this.dailyRecord.getBreakTime().stream().map(d ->
				d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate())).collect(Collectors.toList());
	}

	@Override
	public Optional<AttendanceTimeOfDailyAttendance> attendanceTime() {
		return this.dailyRecord.getAttendanceTime().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

//	@Override
//	public Optional<AttendanceTimeByWorkOfDaily> attendanceTimeByWork() {
//		return this.dailyRecord.getAttendanceTimeByWork().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
//	}

	@Override
	public Optional<TimeLeavingOfDailyAttd> timeLeaving() {
		return this.dailyRecord.getTimeLeaving().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public Optional<ShortTimeOfDailyAttd> shortTime() {
		return this.dailyRecord.getShortWorkTime().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public Optional<SpecificDateAttrOfDailyAttd> specificDateAttr() {
		return this.dailyRecord.getSpecificDateAttr().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate() {
		return this.dailyRecord.getAttendanceLeavingGate().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public Optional<AnyItemValueOfDailyAttd> anyItems() {
		this.dailyRecord.getOptionalItem().ifPresent(c -> {
			if(optionalItems != null) {
				c.correctItems(optionalItems);
			}
		});
		return this.dailyRecord.getOptionalItem().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public List<EditStateOfDailyAttd> editStates() {
		return this.dailyRecord.getEditStates().stream().map(d ->
						d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate())).collect(Collectors.toList());
	}

	@Override
	public Optional<TemporaryTimeOfDailyAttd> temporaryTime() {
		return this.dailyRecord.getTemporaryTime().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public Optional<PCLogOnInfoOfDailyAttd> pcLogInfo() {
		return this.dailyRecord.getPcLogInfo().map(d -> d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate()));
	}

	@Override
	public List<RemarksOfDailyAttd> remarks() {
		return this.dailyRecord.getRemarks().stream().map(d ->
						d.toDomain(this.dailyRecord.employeeId(), this.dailyRecord.workingDate())).collect(Collectors.toList());
	}
}
