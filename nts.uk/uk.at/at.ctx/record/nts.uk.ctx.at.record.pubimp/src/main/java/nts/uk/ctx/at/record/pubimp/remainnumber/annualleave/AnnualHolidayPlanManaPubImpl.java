package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.PlanAnnualUserDetailExport;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlan;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlanManaPub;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanManRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class AnnualHolidayPlanManaPubImpl implements AnnualHolidayPlanManaPub{
	@Inject
	private AnnualHolidayPlanManRepository planRepo;
	@Inject
	private PlanAnnualUserDetailExport detailExport;
	@Inject
	private WorkInformationRepository workInfo;
	@Override
	public List<AnnualHolidayPlan> getDataByPeriod(String employeeId, String workTypeCode, DatePeriod dateData) {
		return planRepo.getDataBySidWorkTypePeriod(employeeId, workTypeCode, dateData).stream()
				.map(x -> new AnnualHolidayPlan(x.getSId(), x.getWorkTypeCd(), x.getYmd(), x.getUseDays().v()))
				.collect(Collectors.toList());
	}

	@Override
	public List<GeneralDate> lstWorkTypePeriod(String employeeId, String workTypeCode, DatePeriod dateData) {
		return detailExport.lstPlanDetail(employeeId, workTypeCode, dateData);
	}

	@Override
	public List<GeneralDate> lstRecordByWorkType(String employeeId, String workTypeCode, DatePeriod period) {
		return workInfo.getByWorkTypeAndDatePeriod(employeeId, workTypeCode, period);
	}

}
