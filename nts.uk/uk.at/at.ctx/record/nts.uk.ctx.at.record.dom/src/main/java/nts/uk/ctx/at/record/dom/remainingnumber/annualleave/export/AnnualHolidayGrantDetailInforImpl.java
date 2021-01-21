package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.DailyInterimRemainMngDataAndFlg;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.arc.time.calendar.period.DatePeriod;
@Stateless
public class AnnualHolidayGrantDetailInforImpl implements AnnualHolidayGrantDetailInfor{
	@Inject
	private GetPeriodFromPreviousToNextGrantDate periodService;
	@Inject	
	private GetAnnualHolidayGrantInfor annGrantInforService;
	@Override
	public List<AnnualHolidayGrantDetail> getAnnHolidayDetail(String cid, String sid, ReferenceAtr referenceAtr, YearMonth ym,
			GeneralDate ymd) {
		List<AnnualHolidayGrantDetail> lstOutputData = new ArrayList<>();
		// 指定した月を基準に、前回付与日から次回付与日までの期間を取得
		Optional<DatePeriod> optDatePeriod = periodService.getPeriodGrantDate(cid, sid, ym, ymd);
		if(!optDatePeriod.isPresent()) {
			return new ArrayList<>();
		}
		DatePeriod datePeriod = optDatePeriod.get();
		//期間内の年休使用明細を取得する
		List<DailyInterimRemainMngDataAndFlg> lstRemainMngData = annGrantInforService.lstRemainData(cid, sid, datePeriod, referenceAtr);
		lstRemainMngData.stream().forEach(x ->{
			TmpAnnualHolidayMng annData = x.getData().getAnnualHolidayData().get();
			x.getData().getRecAbsData().stream().forEach(y -> {
				if(y.getRemainManaID().equals(annData.getAnnualId())) {
					AnnualHolidayGrantDetail annDetail = new AnnualHolidayGrantDetail(sid,
							y.getYmd(),
							annData.getUseDays().v(),
							x.isReferenceFlg()  ? ReferenceAtr.RECORD 
									: (y.getCreatorAtr() == CreateAtr.RECORD ? ReferenceAtr.RECORD : ReferenceAtr.APP_AND_SCHE));
					lstOutputData.add(annDetail);
				}
			});
		});
		return lstOutputData.stream().sorted((a,b) -> a.getYmd().compareTo(b.getYmd()))
				.collect(Collectors.toList());
	}


}
