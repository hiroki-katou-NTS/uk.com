package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;

@Stateless
public class ReflectWorkInformationDomainServiceImpl implements ReflectWorkInformationDomainService{
	@Inject
	private WorkingConditionItemService workingConditionItemService;

	public boolean changeWorkInformation(WorkInfoOfDailyPerformance workInfo,String companyId) {
		WorkInformation recordWorkInformation = workInfo.getWorkInformation().getRecordInfo();
				Optional<SingleDaySchedule> singleDaySchedule = workingConditionItemService
						.getHolidayWorkSchedule(companyId, workInfo.getEmployeeId(), workInfo.getYmd(), recordWorkInformation.getWorkTypeCode().v());
				if(!singleDaySchedule.isPresent()){

					return false;
				}
				return true;
	}

}
