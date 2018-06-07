package nts.uk.ctx.at.request.ac.record.remainingnumber.annualbreakmanage;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlanManaPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaRequest;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
@Stateless
public class AnnualHolidayPlanManaAdapterImpl implements AnnualHolidayPlanManaAdapter{
	@Inject
	private AnnualHolidayPlanManaPub planPub;
	@Override
	public List<AnnualHolidayPlanManaRequest> lstDataByPeriod(String sid, String workTypeCode, Period dateData) {
		return planPub.getDataByPeriod(sid, workTypeCode, dateData)
				.stream()
				.map(x -> new AnnualHolidayPlanManaRequest(x.getSId(), x.getWorkTypeCd(), x.getYmd(), x.getMaxDay()))
				.collect(Collectors.toList());
	}
	@Override
	public List<GeneralDate> lstDetailPeriod(String employeeId, String worktypeCode, Period dateData) {
		return planPub.lstWorkTypePeriod(employeeId, worktypeCode, dateData);
	}
	

}
