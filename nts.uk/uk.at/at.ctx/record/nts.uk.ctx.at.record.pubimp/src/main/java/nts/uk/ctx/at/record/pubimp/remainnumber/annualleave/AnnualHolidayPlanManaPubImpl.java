package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanManRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.PlanAnnualUserDetailExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlan;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlanManaPub;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
@Stateless
public class AnnualHolidayPlanManaPubImpl implements AnnualHolidayPlanManaPub{
	@Inject
	private AnnualHolidayPlanManRepository planRepo;
	@Inject
	private PlanAnnualUserDetailExport detailExport;
	 
	@Override
	public List<AnnualHolidayPlan> getDataByPeriod(String employeeId, String workTypeCode, Period dateData) {
		return planRepo.getDataBySidWorkTypePeriod(employeeId, workTypeCode, dateData).stream()
				.map(x -> new AnnualHolidayPlan(x.getSId(), x.getWorkTypeCd(), x.getYmd(), x.getUseDays().v()))
				.collect(Collectors.toList());
	}

	@Override
	public List<GeneralDate> lstWorkTypePeriod(String employeeId, String workTypeCode, Period dateData) {
		return detailExport.lstPlanDetail(employeeId, workTypeCode, dateData);
	}

}
