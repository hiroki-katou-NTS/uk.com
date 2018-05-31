package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.AffiliationInforOfDailyPerforFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.BusinessTypeOfDailyPerforFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.OutingTimeOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.OptionalItemOfDailyPerformFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.PCLogOnInforOfDailyPerformFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto.PCLogOnInforOfDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.RemarksOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarksOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.BreakTimeDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.ShortTimeOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.SpecificDateAttrOfDailyPerforFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.WorkInformationOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.AttendanceTimeByWorkOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.TimeLeavingOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DailyRecordWorkFinder extends FinderFacade {

	@Inject
	private AttendanceTimeOfDailyPerformFinder attendanceTimeFinder;
	
	@Inject
	private AffiliationInforOfDailyPerforFinder affiliInfoFinder;
	
	@Inject
	private BusinessTypeOfDailyPerforFinder businessTypeFinder;
	
	@Inject
	private AttendanceLeavingGateOfDailyFinder attendanceLeavingGateFinder;
	
	@Inject
	private OutingTimeOfDailyPerformanceFinder outingTimeFinder;
	
	@Inject
	private OptionalItemOfDailyPerformFinder optionalItemFinder;
	
	@Inject
	private PCLogOnInforOfDailyPerformFinder pcLogOnInfoFinder;
	
	@Inject
	private BreakTimeDailyFinder breakItemFinder;
	
	@Inject
	private SpecificDateAttrOfDailyPerforFinder specificDateAttrFinder;
	
	@Inject
	private TemporaryTimeOfDailyPerformanceFinder temporaryTimeFinder;
	
	@Inject
	private WorkInformationOfDailyFinder workInfoFinder;
	
	@Inject
	private TimeLeavingOfDailyPerformanceFinder timeLeavingFinder;
	
	@Inject
	private CalcAttrOfDailyPerformanceFinder calcAttrFinder;
	
	@Inject
	private ShortTimeOfDailyFinder shortWorkFinder;
	
	@Inject
	private EditStateOfDailyPerformanceFinder editStateFinder;
	// @Inject
	// private EmployeeDailyPerErrorFinder errorFinder;
	@Inject
	private AttendanceTimeByWorkOfDailyFinder attendanceTimeByWorkFinder;
	
	@Inject
	private RemarksOfDailyFinder remarkFinder;
	
	@Override
	public FinderFacade getFinder(String layout){
		FinderFacade finder = null;
		switch (layout) {
		case "A":
			finder = this.workInfoFinder;
			break;
		case "B":
			finder = this.calcAttrFinder;
			break;
		case "C":
			finder = this.affiliInfoFinder;
			break;
		case "D":
			finder = this.businessTypeFinder;
			break;
		case "E":
			finder = this.outingTimeFinder;
			break;
		case "F":
			finder = this.breakItemFinder;
			break;
		case "G":
			finder = this.attendanceTimeFinder;
			break;
		case "H":
			finder = this.attendanceTimeByWorkFinder;
			break;
		case "I":
			finder = this.timeLeavingFinder;
			break;
		case "J":
			finder = this.shortWorkFinder;
			break;
		case "K":
			finder = this.specificDateAttrFinder;
			break;
		case "L":
			finder = this.attendanceLeavingGateFinder;
			break;
		case "M":
			finder = this.optionalItemFinder;
			break;
		case "N":
			finder = this.editStateFinder;
			break;
		case "O":
			finder = this.temporaryTimeFinder;
			break;
		case "P":
			finder = this.pcLogOnInfoFinder;
			break;
		case "Q":
			finder = this.remarkFinder;
			break;
		default:
			break;
		}
		return finder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DailyRecordDto find(String employeeId, GeneralDate baseDate) {
		return DailyRecordDto.builder().employeeId(employeeId).workingDate(baseDate)
				.withWorkInfo(workInfoFinder.find(employeeId, baseDate))
				.withCalcAttr(calcAttrFinder.find(employeeId, baseDate))
				.withAffiliationInfo(affiliInfoFinder.find(employeeId, baseDate))
				.withBusinessType(businessTypeFinder.find(employeeId, baseDate))
				// .withErrors(errorFinder.find(employeeId, baseDate))
				.outingTime(outingTimeFinder.find(employeeId, baseDate))
				.addBreakTime(breakItemFinder.finds(employeeId, baseDate))
				.attendanceTime(attendanceTimeFinder.find(employeeId, baseDate))
				.attendanceTimeByWork(attendanceTimeByWorkFinder.find(employeeId, baseDate))
				.timeLeaving(timeLeavingFinder.find(employeeId, baseDate))
				.shortWorkTime(shortWorkFinder.find(employeeId, baseDate))
				.specificDateAttr(specificDateAttrFinder.find(employeeId, baseDate))
				.attendanceLeavingGate(attendanceLeavingGateFinder.find(employeeId, baseDate))
				.optionalItems(optionalItemFinder.find(employeeId, baseDate))
				.addEditStates(editStateFinder.finds(employeeId, baseDate))
				.temporaryTime(temporaryTimeFinder.find(employeeId, baseDate))
				.pcLogInfo(pcLogOnInfoFinder.find(employeeId, baseDate))
				.remarks(remarkFinder.finds(employeeId, baseDate))
				.complete();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(List<String> employeeId, DatePeriod baseDate) {
		Map<String, Map<GeneralDate, WorkInformationOfDailyDto>> workInfos = toMap(
				workInfoFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, CalcAttrOfDailyPerformanceDto>> calcAttrs = toMap(
				calcAttrFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, AffiliationInforOfDailyPerforDto>> affiliInfo = toMap(
				affiliInfoFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, BusinessTypeOfDailyPerforDto>> businessType = toMap(
				businessTypeFinder.find(employeeId, baseDate));
		// Map<String, Map<GeneralDate, EmployeeDailyPerErrorDto>> errors =
		// toMap(errorFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, OutingTimeOfDailyPerformanceDto>> outings = toMap(
				outingTimeFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, List<BreakTimeDailyDto>>> breaks = toMapList(
				breakItemFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, AttendanceTimeDailyPerformDto>> attendTime = toMap(
				attendanceTimeFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, AttendanceTimeByWorkOfDailyDto>> attendTimeByWork = toMap(
				attendanceTimeByWorkFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, TimeLeavingOfDailyPerformanceDto>> leaving = toMap(
				timeLeavingFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, ShortTimeOfDailyDto>> shortWork = toMap(
				shortWorkFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, SpecificDateAttrOfDailyPerforDto>> specificDateAttr = toMap(
				specificDateAttrFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, AttendanceLeavingGateOfDailyDto>> attendLeavingGate = toMap(
				attendanceLeavingGateFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, OptionalItemOfDailyPerformDto>> optionalItems = toMap(
				optionalItemFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, List<EditStateOfDailyPerformanceDto>>> editStates = toMapList(
				editStateFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, TemporaryTimeOfDailyPerformanceDto>> temporaryTime = toMap(
				temporaryTimeFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, PCLogOnInforOfDailyPerformDto>> pcLogInfo = toMap(
				pcLogOnInfoFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, List<RemarksOfDailyDto>>> remarks = toMapList(remarkFinder.find(employeeId, baseDate));

		return (List<T>) employeeId.stream().map(em -> {
			List<DailyRecordDto> dtoByDates = new ArrayList<>();
			GeneralDate start = baseDate.start();
			while (start.beforeOrEquals(baseDate.end())) {
				WorkInformationOfDailyDto workInfo = getValue(workInfos.get(em), start);
				if(workInfo != null && workInfo.isHaveData()){
					DailyRecordDto current = DailyRecordDto.builder().employeeId(em).workingDate(start)
							.withWorkInfo(workInfo)
							.withCalcAttr(getValue(calcAttrs.get(em), start))
							.withAffiliationInfo(getValue(affiliInfo.get(em), start))
							.withBusinessType(getValue(businessType.get(em), start))
							// .withErrors(getValue(errors.get(em), start))
							.outingTime(getValue(outings.get(em), start))
							.addBreakTime(getListValue(breaks.get(em), start))
							.attendanceTime(getValue(attendTime.get(em), start))
							.attendanceTimeByWork(getValue(attendTimeByWork.get(em), start))
							.timeLeaving(getValue(leaving.get(em), start))
							.shortWorkTime(getValue(shortWork.get(em), start))
							.specificDateAttr(getValue(specificDateAttr.get(em), start))
							.attendanceLeavingGate(getValue(attendLeavingGate.get(em), start))
							.optionalItems(getValue(optionalItems.get(em), start))
							.addEditStates(getListValue(editStates.get(em), start))
							.temporaryTime(getValue(temporaryTime.get(em), start))
							.pcLogInfo(getValue(pcLogInfo.get(em), start))
							.remarks(getListValue(remarks.get(em), start))
							.complete();
					dtoByDates.add(current);
				}
				start = start.addDays(1);
			}
			return dtoByDates;
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ConvertibleAttendanceItem> List<T> find(Map<String, List<GeneralDate>> param) {
		Map<String, Map<GeneralDate, WorkInformationOfDailyDto>> workInfos = toMap(
				workInfoFinder.find(param));
		Map<String, Map<GeneralDate, CalcAttrOfDailyPerformanceDto>> calcAttrs = toMap(
				calcAttrFinder.find(param));
		Map<String, Map<GeneralDate, AffiliationInforOfDailyPerforDto>> affiliInfo = toMap(
				affiliInfoFinder.find(param));
		Map<String, Map<GeneralDate, BusinessTypeOfDailyPerforDto>> businessType = toMap(
				businessTypeFinder.find(param));
		// Map<String, Map<GeneralDate, EmployeeDailyPerErrorDto>> errors =
		// toMap(errorFinder.find(employeeId, baseDate));
		Map<String, Map<GeneralDate, OutingTimeOfDailyPerformanceDto>> outings = toMap(
				outingTimeFinder.find(param));
		Map<String, Map<GeneralDate, List<BreakTimeDailyDto>>> breaks = toMapList(
				breakItemFinder.find(param));
		Map<String, Map<GeneralDate, AttendanceTimeDailyPerformDto>> attendTime = toMap(
				attendanceTimeFinder.find(param));
		Map<String, Map<GeneralDate, AttendanceTimeByWorkOfDailyDto>> attendTimeByWork = toMap(
				attendanceTimeByWorkFinder.find(param));
		Map<String, Map<GeneralDate, TimeLeavingOfDailyPerformanceDto>> leaving = toMap(
				timeLeavingFinder.find(param));
		Map<String, Map<GeneralDate, ShortTimeOfDailyDto>> shortWork = toMap(
				shortWorkFinder.find(param));
		Map<String, Map<GeneralDate, SpecificDateAttrOfDailyPerforDto>> specificDateAttr = toMap(
				specificDateAttrFinder.find(param));
		Map<String, Map<GeneralDate, AttendanceLeavingGateOfDailyDto>> attendLeavingGate = toMap(
				attendanceLeavingGateFinder.find(param));
		Map<String, Map<GeneralDate, OptionalItemOfDailyPerformDto>> optionalItems = toMap(
				optionalItemFinder.find(param));
		Map<String, Map<GeneralDate, List<EditStateOfDailyPerformanceDto>>> editStates = toMapList(
				editStateFinder.find(param));
		Map<String, Map<GeneralDate, TemporaryTimeOfDailyPerformanceDto>> temporaryTime = toMap(
				temporaryTimeFinder.find(param));
		Map<String, Map<GeneralDate, PCLogOnInforOfDailyPerformDto>> pcLogInfo = toMap(
				pcLogOnInfoFinder.find(param));
		Map<String, Map<GeneralDate, List<RemarksOfDailyDto>>> remarks = toMapList(remarkFinder.find(param));

		return (List<T>) param.entrySet().stream().map(p -> {
			return p.getValue().stream().map(d -> {
				return DailyRecordDto.builder()
						.employeeId(p.getKey())
						.workingDate(d)
						.withWorkInfo(getValue(workInfos.get(p.getKey()),d))
						.withCalcAttr(getValue(calcAttrs.get(p.getKey()), d))
						.withAffiliationInfo(getValue(affiliInfo.get(p.getKey()), d))
						.withBusinessType(getValue(businessType.get(p.getKey()), d))
						// .withErrors(getValue(errors.get(em), start))
						.outingTime(getValue(outings.get(p.getKey()), d))
						.addBreakTime(getListValue(breaks.get(p.getKey()), d))
						.attendanceTime(getValue(attendTime.get(p.getKey()), d))
						.attendanceTimeByWork(getValue(attendTimeByWork.get(p.getKey()), d))
						.timeLeaving(getValue(leaving.get(p.getKey()), d))
						.shortWorkTime(getValue(shortWork.get(p.getKey()), d))
						.specificDateAttr(getValue(specificDateAttr.get(p.getKey()), d))
						.attendanceLeavingGate(getValue(attendLeavingGate.get(p.getKey()), d))
						.optionalItems(getValue(optionalItems.get(p.getKey()), d))
						.addEditStates(getListValue(editStates.get(p.getKey()), d))
						.temporaryTime(getValue(temporaryTime.get(p.getKey()), d))
						.pcLogInfo(getValue(pcLogInfo.get(p.getKey()), d))
						.remarks(getListValue(remarks.get(p.getKey()), d))
						.complete();
			}).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	private <T extends ConvertibleAttendanceItem> T getValue(Map<GeneralDate, T> data, GeneralDate date) {
		return data == null ? null : data.get(date);
	}

	private <T extends ConvertibleAttendanceItem> List<T> getListValue(Map<GeneralDate, List<T>> data,
			GeneralDate date) {
		return data == null ? new ArrayList<>() : data.get(date);
	}

	private <T extends ConvertibleAttendanceItem> Map<String, Map<GeneralDate, T>> toMap(List<T> dtos) {
		return dtos.stream()
				.collect(Collectors.groupingBy(c -> c.employeeId(), Collectors.toMap(x -> x.workingDate(), x -> x)));
	}

	private <T extends ConvertibleAttendanceItem> Map<String, Map<GeneralDate, List<T>>> toMapList(List<T> dtos) {
		return dtos.stream()
				.collect(Collectors.groupingBy(c -> c.employeeId(), Collectors.groupingBy(x -> x.workingDate())));
	}
}
