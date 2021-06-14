package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.DailyInterimRemainMngDataAndFlg;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.arc.time.calendar.period.DatePeriod;
@Stateless
public class AnnualHolidayGrantDetailInforImpl implements AnnualHolidayGrantDetailInfor{
	@Inject
	private GetPeriodFromPreviousToNextGrantDate periodService;
	@Inject
	private GetAnnualHolidayGrantInfor annGrantInforService;
	@Inject
	private WorkTypeRepository worktypeRepo;
	@Override
	public List<AnnualHolidayGrantDetail> getAnnHolidayDetail(String cid, String sid, ReferenceAtr referenceAtr,
			YearMonth ym, GeneralDate ymd, Integer targetPeriod, Optional<DatePeriod> fromTo,
			Optional<GeneralDate> doubleTrackStartDate) {
		List<AnnualHolidayGrantDetail> lstOutputData = new ArrayList<>();
		// 指定した月を基準に、前回付与日から次回付与日までの期間を取得 - 1 2 3
		Optional<DatePeriod> optDatePeriod = periodService.getPeriodGrantDate(cid, sid, ym, ymd, targetPeriod, fromTo)
				.map(GrantPeriodDto::getPeriod);
		if(!optDatePeriod.isPresent()) {
			return new ArrayList<>();
		}
		DatePeriod datePeriod = optDatePeriod.get();
		GeneralDate startDate = datePeriod.start();
		if (doubleTrackStartDate.isPresent()) {
			startDate = doubleTrackStartDate.get();
		}
		// 期間内の年休使用明細を取得する - 4
		List<DailyInterimRemainMngDataAndFlg> lstRemainMngData = annGrantInforService.lstRemainData(
				cid,
				sid,
				new DatePeriod(startDate, datePeriod.end()),
				referenceAtr);

		// 暫定年休管理データを年休使用詳細へ変換
		lstRemainMngData.stream().forEach(x ->{
			x.getData().getAnnualHolidayData().forEach(annData->{
				Optional<WorkType> getWorkType = worktypeRepo.findByPK(cid, annData.getWorkTypeCode().v());
				DailyWork dw =  getWorkType.isPresent() ? getWorkType.get().getDailyWork() : null;
				Integer vacation = dw == null ? null : (dw.isOneDay() ? 0 : (dw.IsLeaveForMorning() ? 1 : 2));
				// Fix bug in case of flex
				if (vacation == null && annData.getCreatorAtr().equals(CreateAtr.FLEXCOMPEN)) {
					vacation = annData.getUsedNumber().getDays().greaterThan(0.5) ? 0 : 1;
				}

				AnnualHolidayGrantDetail annDetail = new AnnualHolidayGrantDetail(sid, annData.getYmd(),
						AnnualLeaveUsedNumber.of(
								Optional.ofNullable(new AnnualLeaveUsedDayNumber(annData.getUsedNumber().getDays().v())),
								Optional.ofNullable(new UsedMinutes(
										annData.getUsedNumber().getMinutes().map(minute -> minute.v()).orElse(0)))),
						x.isReferenceFlg() ? ReferenceAtr.RECORD
								: ((annData.getCreatorAtr() == CreateAtr.RECORD
										|| annData.getCreatorAtr() == CreateAtr.FLEXCOMPEN) ? ReferenceAtr.RECORD
												: ReferenceAtr.APP_AND_SCHE),
						AmPmAtr.valueOf(vacation),
						annData.getCreatorAtr().equals(CreateAtr.FLEXCOMPEN));
				lstOutputData.add(annDetail);
			});
		});
		// 年休使用詳細(List)を返す
		return lstOutputData.stream().sorted((a,b) -> a.getYmd().compareTo(b.getYmd()))
				.collect(Collectors.toList());
	}


}
