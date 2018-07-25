package nts.uk.ctx.at.request.ac.record.remainingnumber.annualbreakmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlanManaPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaRequest;
import nts.uk.ctx.at.request.dom.vacation.history.service.PeriodVactionCalInfor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class AnnualHolidayPlanManaAdapterImpl implements AnnualHolidayPlanManaAdapter{
	@Inject
	private AnnualHolidayPlanManaPub planPub;
	@Override
	public List<AnnualHolidayPlanManaRequest> lstDataByPeriod(String sid, String workTypeCode, DatePeriod dateData) {
		return planPub.getDataByPeriod(sid, workTypeCode, dateData)
				.stream()
				.map(x -> new AnnualHolidayPlanManaRequest(x.getSId(), x.getWorkTypeCd(), x.getYmd(), x.getMaxDay()))
				.collect(Collectors.toList());
	}
	@Override
	public List<GeneralDate> lstDetailPeriod(String cid, String employeeId, String worktypeCode, PeriodVactionCalInfor getCalByDate) {
		List<GeneralDate> lstOutput = new ArrayList<>();
		//INPUT．日別実績取得するフラグをチェックする
		if(getCalByDate.isChkRecordData() 
				&& getCalByDate.getRecordDate().isPresent()) {
			//ドメインモデル「日別実績の勤務情報」を取得する
			List<GeneralDate> lstRecordTime = planPub.lstRecordByWorkType(employeeId, worktypeCode, getCalByDate.getRecordDate().get());
			if(!lstRecordTime.isEmpty()) {
				lstOutput.addAll(lstRecordTime);
			}
		}
		if(getCalByDate.isChkInterimData()
				&& getCalByDate.getInterimDate().isPresent()) {
			List<GeneralDate> lstInterim = planPub.lstWorkTypePeriod(employeeId, worktypeCode, getCalByDate.getInterimDate().get());
			if(!lstInterim.isEmpty()) {
				lstOutput.addAll(lstInterim);
			}
		}
		return lstOutput;
	}
	

}
