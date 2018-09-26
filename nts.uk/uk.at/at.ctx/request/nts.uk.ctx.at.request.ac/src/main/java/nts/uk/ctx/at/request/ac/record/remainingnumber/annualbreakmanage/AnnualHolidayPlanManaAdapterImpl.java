package nts.uk.ctx.at.request.ac.record.remainingnumber.annualbreakmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnualHolidayPlanManaPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualHolidayPlanManaRequest;
import nts.uk.ctx.at.request.dom.vacation.history.service.PeriodVactionCalInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class AnnualHolidayPlanManaAdapterImpl implements AnnualHolidayPlanManaAdapter{
	@Inject
	private AnnualHolidayPlanManaPub planPub;
	@Inject
	private InterimRemainRepository interimMng;
	@Inject
	private TmpAnnualHolidayMngRepository annualMng;
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
			//取得した「日別実績の勤務情報」を「使用一覧」に追加する
			if(!lstRecordTime.isEmpty()) {
				lstOutput.addAll(lstRecordTime);
			}
		}
		if(getCalByDate.isChkInterimData()
				&& getCalByDate.getInterimDate().isPresent()) {		
			//INPUT．暫定データ取得するフラグをチェックする
			//ドメインモデル「暫定残数管理データ」を取得する
			List<InterimRemain> getRemainBySidPriod = interimMng.getRemainBySidPriod(employeeId, getCalByDate.getInterimDate().get(), RemainType.ANNUAL);
			getRemainBySidPriod = getRemainBySidPriod.stream().filter(x -> x.getRemainAtr() == RemainAtr.SINGLE)
					.collect(Collectors.toList());
			getRemainBySidPriod.stream().forEach(a -> {
				Optional<TmpAnnualHolidayMng> getById = annualMng.getById(a.getRemainManaID());
				if(getById.isPresent() && getById.get().getWorkTypeCode().equals(worktypeCode)) {
					lstOutput.add(a.getYmd());
				}
			});
		}
		return lstOutput;
	}
	

}
