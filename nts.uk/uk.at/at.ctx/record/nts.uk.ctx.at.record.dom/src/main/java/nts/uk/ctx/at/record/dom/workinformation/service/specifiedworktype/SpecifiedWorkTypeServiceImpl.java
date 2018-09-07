package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class SpecifiedWorkTypeServiceImpl implements SpecifiedWorkTypeService {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Override
	public DailyWorkTypeList getNumberOfSpecifiedWorkType(String employeeId, List<WorkTypeCode> workTypeList, GeneralDate startDate,
			GeneralDate endDate) {
		
		DailyWorkTypeList dailyWorkTypeList = new DailyWorkTypeList();
		
		List<WorkInfoOfDailyPerformance> workInfoList = this.workInformationRepository
				.findByPeriodOrderByYmd(employeeId, new DatePeriod(startDate, endDate)).stream()
				.sorted((p1, p2) -> p1.getYmd().compareTo(p2.getYmd())).collect(Collectors.toList());

		
		List<NumberOfWorkTypeUsed> numberOfWorkTypeUsedList = new ArrayList<>();
		
		for (WorkTypeCode WorkTypeCode : workTypeList) {
			Double count = (double) 0;
			if (!workInfoList.isEmpty()) {
				List<WorkInfoOfDailyPerformance> workInfoListNew = workInfoList.stream()
						.filter(item -> item.getRecordInfo().getWorkTypeCode().equals(WorkTypeCode))
						.collect(Collectors.toList());

				count = new Double(workInfoListNew.size());
			}
			
			NumberOfWorkTypeUsed numberOfWorkTypeUsed = new NumberOfWorkTypeUsed();
			numberOfWorkTypeUsed.setAttendanceDaysMonth(new AttendanceDaysMonth(count));
			numberOfWorkTypeUsed.setWorkTypeCode(WorkTypeCode);
			numberOfWorkTypeUsedList.add(numberOfWorkTypeUsed);
		}
		
		dailyWorkTypeList.setEmployeeId(employeeId);
		dailyWorkTypeList.setNumberOfWorkTypeUsedList(numberOfWorkTypeUsedList);
		return dailyWorkTypeList;
	}

}
