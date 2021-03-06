package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class SpecifiedWorkTypeServiceImpl implements SpecifiedWorkTypeService {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private EmployeeRecordAdapter employeeRecordAdapter;
	
	@Inject
	private TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepository;
	@Inject 
	private RecordDomRequireService requireService;

	@Override
	public DailyWorkTypeList getNumberOfSpecifiedWorkType(String employeeId, List<WorkTypeCode> workTypeList,
			GeneralDate startDate, GeneralDate endDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		DatePeriod period = new DatePeriod(startDate, endDate);
		DailyWorkTypeList dailyWorkTypeList = new DailyWorkTypeList();

		List<NumberOfWorkTypeUsed> numberOfWorkTypeUsedList = new ArrayList<>();
		List<NumberOfWorkTypeUsed> newNumberOfWorkTypeUsedList = new ArrayList<>();

		// ??????????????????????????????????????????????????????????????????????????????????????????
		Optional<GeneralDate> optionalTime = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);

		if (optionalTime.isPresent()) {
			// ??????????????????????????????????????????
			RemoveRetirementDto retirementDto = this.removeRetirement(period, employeeId);

			if (retirementDto.getStatus() == false) {
				// ???????????????????????????????????????
				// ???????????????????????????????????????(List)???????????????
				for (WorkTypeCode WorkTypeCode : workTypeList) {
					Double count = (double) 0;

					NumberOfWorkTypeUsed numberOfWorkTypeUsed = new NumberOfWorkTypeUsed();
					numberOfWorkTypeUsed.setAttendanceDaysMonth(new AttendanceDaysMonth(count));
					numberOfWorkTypeUsed.setWorkTypeCode(WorkTypeCode);
					newNumberOfWorkTypeUsedList.add(numberOfWorkTypeUsed);
				}
			} else {
				// ??????????????????????????????????????????????????????????????????
				DatePeriod datePeriod = retirementDto.getPeriod();

				// create ??????????????????
				DatePeriod resultPeriod = null;
				// create ???????????????????????????
				DatePeriod tempPeriod = null;
				if (optionalTime.get().before(datePeriod.start())) {
					tempPeriod = datePeriod;
				} else if (optionalTime.get().afterOrEquals(datePeriod.start())
						&& optionalTime.get().beforeOrEquals(datePeriod.end())) {
					resultPeriod = new DatePeriod(datePeriod.start(), optionalTime.get().addDays(-1));
					tempPeriod = new DatePeriod(optionalTime.get(), datePeriod.end());
				} else if (optionalTime.get().after(datePeriod.end())) {
					resultPeriod = datePeriod;
				}
				
				// ???????????????????????????????????????????????????
				if (resultPeriod != null) {
					List<WorkInfoOfDailyPerformance> workInfoList = this.workInformationRepository
							.findByPeriodOrderByYmd(employeeId, resultPeriod).stream()
							.sorted((p1, p2) -> p1.getYmd().compareTo(p2.getYmd())).collect(Collectors.toList());

					for (WorkTypeCode workTypeCode : workTypeList) {
						Double count = (double) 0;
						if (!workInfoList.isEmpty()) {
							List<WorkInfoOfDailyPerformance> workInfoListNew = workInfoList.stream()
									.filter(item -> item.getWorkInformation().getRecordInfo().getWorkTypeCode().equals(workTypeCode.v()))
									.collect(Collectors.toList());

							count = new Double(workInfoListNew.size());
						}

						NumberOfWorkTypeUsed numberOfWorkTypeUsed = new NumberOfWorkTypeUsed();
						numberOfWorkTypeUsed.setAttendanceDaysMonth(new AttendanceDaysMonth(count));
						numberOfWorkTypeUsed.setWorkTypeCode(workTypeCode);
						numberOfWorkTypeUsedList.add(numberOfWorkTypeUsed);
					}
				}
				
				// ???????????????????????????????????????????????????????????????
				if (tempPeriod != null) {
					List<TempAnnualLeaveMngs> interimRemains = this.tmpAnnualHolidayMngRepository.getBySidPeriod(employeeId, tempPeriod);
					
					for (WorkTypeCode workTypeCode : workTypeList) {
						Double count = (double) 0;
						
						if (!interimRemains.isEmpty()) {
							List<TempAnnualLeaveMngs> listNew = interimRemains.stream()
									.filter(item -> item.getWorkTypeCode().equals(workTypeCode.v()))
									.collect(Collectors.toList());

							count = new Double(listNew.size());
						}

						NumberOfWorkTypeUsed numberOfWorkTypeUsed = new NumberOfWorkTypeUsed();
						numberOfWorkTypeUsed.setAttendanceDaysMonth(new AttendanceDaysMonth(count));
						numberOfWorkTypeUsed.setWorkTypeCode(workTypeCode);
						numberOfWorkTypeUsedList.add(numberOfWorkTypeUsed);
					}
				}
				// ???????????????????????????????????????????????????
				// groupBy (workTypeCode, List<NumberOfWorkTypeUsed> )
				Map<WorkTypeCode, List<NumberOfWorkTypeUsed>> numberOfWorkTypeMap = numberOfWorkTypeUsedList.stream().collect(Collectors.groupingBy(item -> item.getWorkTypeCode()));
				
				numberOfWorkTypeMap.forEach((key, value) -> {
					// calculate sum of list
					Double sum = value.stream().map(item -> item.getAttendanceDaysMonth().v()).reduce((double) 0, Double::sum);
					
					NumberOfWorkTypeUsed numberOfWorkTypeUsed = new NumberOfWorkTypeUsed();
					numberOfWorkTypeUsed.setAttendanceDaysMonth(new AttendanceDaysMonth(sum));
					numberOfWorkTypeUsed.setWorkTypeCode(key);
					newNumberOfWorkTypeUsedList.add(numberOfWorkTypeUsed);
				});
			}
		}
		
		dailyWorkTypeList.setEmployeeId(employeeId);
		dailyWorkTypeList.setNumberOfWorkTypeUsedList(newNumberOfWorkTypeUsedList);
		return dailyWorkTypeList;
	}

	private RemoveRetirementDto removeRetirement(DatePeriod period, String employeeId) {

		RemoveRetirementDto result = new RemoveRetirementDto();
		result.setPeriod(period);
		result.setStatus(false);
		DatePeriod newPeriod = period;

		// RequestList1
		EmployeeRecordImport empInfo = employeeRecordAdapter.getPersonInfor(employeeId);

		if (empInfo != null) {
			// ????????????????????????????????????????????????????????????
			if (newPeriod.end().before(empInfo.getEntryDate())) {
				return result;
			} else if (newPeriod.start().before(empInfo.getEntryDate())
					&& newPeriod.end().afterOrEquals(empInfo.getEntryDate())) {
				newPeriod = new DatePeriod(empInfo.getEntryDate(), newPeriod.end());
			}

			// ????????????????????????????????????????????????????????????
			if (newPeriod.start().after(empInfo.getRetiredDate())) {
				result.setPeriod(newPeriod);
				result.setStatus(false);
				return result;
			} else if (newPeriod.start().beforeOrEquals(empInfo.getRetiredDate())
					&& newPeriod.end().after(empInfo.getRetiredDate())) {
				newPeriod = new DatePeriod(newPeriod.start(), empInfo.getRetiredDate());
			}

			result.setPeriod(newPeriod);
			result.setStatus(true);
		}

		return result;
	}

}
