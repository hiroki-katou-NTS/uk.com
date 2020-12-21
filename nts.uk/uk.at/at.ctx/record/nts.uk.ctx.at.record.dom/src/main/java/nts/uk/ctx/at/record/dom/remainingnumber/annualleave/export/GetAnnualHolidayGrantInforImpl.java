package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.CreateInterimAnnualMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.DailyInterimRemainMngDataAndFlg;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
@Stateless
public class GetAnnualHolidayGrantInforImpl implements GetAnnualHolidayGrantInfor{
	@Inject
	private GetPeriodFromPreviousToNextGrantDate periodGrantInfor;
	@Inject
	private AnnualLeaveRemainHistRepository annualRepo;
	@Inject
	private TmpAnnualHolidayMngRepository annualRepository;
	@Inject
	private InterimRemainRepository interimRepo;
	@Inject
	private AnnualLeaveTimeRemainHistRepository annTimeRemainHisRepo;
	/** 月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	@Inject
	private AnnLeaGrantRemDataRepository annLeaRemRepo;
	@Inject
	private RecordDomRequireService requireService;

	@Override
	public Optional<AnnualHolidayGrantInfor> getAnnGrantInfor(String cid, String sid, ReferenceAtr referenceAtr,
			YearMonth ym, GeneralDate ymd) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		//指定した月を基準に、前回付与日から次回付与日までの期間を取得
		Optional<DatePeriod> optPeriod = periodGrantInfor.getPeriodGrantDate(cid, sid, ym, ymd);
		if(!optPeriod.isPresent()) {
			return Optional.empty();
		}
		DatePeriod period = optPeriod.get();
		AnnualHolidayGrantInfor outPut = new AnnualHolidayGrantInfor(sid, period.end().addDays(1), new ArrayList<>());
		//社員に対応する処理締めを取得する
		Closure closureOfEmp = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, ymd);
		//指定月の締め開始日を取得
		Optional<GeneralDate> optStartDate = this.getStartDateByClosure(sid, ym, closureOfEmp.getClosureMonth().getProcessingYm(), ymd);
		if(!optStartDate.isPresent()) {
			return Optional.of(outPut);
		}
		GeneralDate startDate = optStartDate.get();
		//期間内の年休使用明細を取得する
		List<DailyInterimRemainMngDataAndFlg> lstUseInfor = this.lstRemainData(cid,
				sid,
				new DatePeriod(startDate, period.end()),
				referenceAtr);
		List<DailyInterimRemainMngData> lstRemainData = lstUseInfor.stream().map(x -> {
			return x.getData();
		}).collect(Collectors.toList());
		List<TmpAnnualLeaveMngWork> lstTmpAnnual = new ArrayList<>();
		for (DailyInterimRemainMngData remainMng : lstRemainData) {
			TmpAnnualHolidayMng annData = remainMng.getAnnualHolidayData().get();
			InterimRemain remainData = remainMng.getRecAbsData()
					.stream()
					.filter(x -> x.getRemainManaID().equals(annData.getAnnualId()))
					.collect(Collectors.toList()).get(0);
			TmpAnnualLeaveMngWork tmpAnnual = TmpAnnualLeaveMngWork.of(remainData, annData);
			lstTmpAnnual.add(tmpAnnual);
		}

		//期間中の年休残数を取得
		boolean isPastMonth = ym.greaterThanOrEqualTo(closureOfEmp.getClosureMonth().getProcessingYm()) ? false : true;
		Optional<AggrResultOfAnnualLeave> optAnnualLeaveRemain = GetAnnLeaRemNumWithinPeriodProc
				.algorithm(require, cacheCarrier, cid,
					sid,
					new DatePeriod(startDate, period.end()),
					InterimRemainMngMode.MONTHLY,
					startDate,
//					false,
					false,
					Optional.of(true),//上書きフラグ
					Optional.of(lstTmpAnnual), //上書き用の暫定年休管理データ
					Optional.empty(),//前回の年休の集計結果
					Optional.of(true),//集計開始日を締め開始日とする
//					Optional.of(false), //不足分付与残数データ出力区分
//					Optional.of(isPastMonth),//過去月集計モード
					Optional.of(ym)); //年月
		if(!optAnnualLeaveRemain.isPresent()) {
			return Optional.of(outPut);
		}
		AggrResultOfAnnualLeave annualLeaveRemain = optAnnualLeaveRemain.get();
		//指定月時点の使用数を計算
		List<AnnualLeaveGrantRemainingData> lstAnnRemainHis = this.lstRemainHistory(sid,
				annualLeaveRemain.getAsOfPeriodEnd().getGrantRemainingNumberList(), period.start());
		if(!lstAnnRemainHis.isEmpty()) {
			List<AnnualHolidayGrant> lstAnnHolidayGrant = new ArrayList<>();
			for (AnnualLeaveGrantRemainingData a : lstAnnRemainHis) {
				AnnualHolidayGrant grantData = new AnnualHolidayGrant(a.getGrantDate(),
						a.getDetails().getGrantNumber().getDays().v(),
						a.getDetails().getUsedNumber().getDays().v(),
						a.getDetails().getRemainingNumber().getDays().v());
				lstAnnHolidayGrant.add(grantData);
			}
			outPut.setLstGrantInfor(lstAnnHolidayGrant);
		}
		//指定年月の締め日を取得
		List<ClosureHistory> closureHistories = closureOfEmp.getClosureHistories().stream()
				.filter(x -> x.getStartYearMonth().lessThanOrEqualTo(ym) && x.getEndYearMonth().greaterThanOrEqualTo(ym))
				.collect(Collectors.toList());
		//前回付与日～INPUT．指定年月の間で期限が切れた付与情報を取得
		closureHistories.stream().forEach(x -> {
			List<AnnualHolidayGrant> grantInforFormPeriod = this.grantInforFormPeriod(sid, ym, x.getClosureId(),
					x.getClosureDate(), new DatePeriod(period.start(), startDate.addDays(-1)), isPastMonth);
			if(!grantInforFormPeriod.isEmpty()) {
				outPut.getLstGrantInfor().addAll(grantInforFormPeriod);
			}
		});

		return Optional.of(outPut);
	}

	/**
	 * 指定月の締め開始日を取得
	 * @param sid
	 * @param ym 指定月
	 * @param processingYm 締めの当月
	 * @param ymd 基準日
	 * @return
	 */
	public Optional<GeneralDate> getStartDateByClosure(String sid, YearMonth ym, YearMonth processingYm, GeneralDate ymd) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		//INPUT．指定月が当月かどうかを判断する
		if(ym.greaterThanOrEqualTo(processingYm)) {
			//社員に対応する締め期間を取得する
			DatePeriod period = ClosureService.findClosurePeriod(require, cacheCarrier, sid, ymd);
			return Optional.of(period.start());
		}
		//ドメインモデル「年休付与残数履歴データ」を取得
		List<AnnualLeaveRemainingHistory> lstAnn = annualRepo.getInfoBySidAndYM(sid, ym)
				.stream().sorted((a,b)
						-> GeneralDate.ymd(a.getYearMonth().year(), a.getYearMonth().month(), a.getClosureDate().getClosureDay().v())
						.compareTo(GeneralDate.ymd(b.getYearMonth().year(), b.getYearMonth().month(), b.getClosureDate().getClosureDay().v())))
				.collect(Collectors.toList());
		//指定した年月の期間を算出する
		if(lstAnn.isEmpty()) {
			return Optional.empty();
		}
		AnnualLeaveRemainingHistory annRemainHisInfor = lstAnn.get(0);
		DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(require, annRemainHisInfor.getClosureId().value, annRemainHisInfor.getYearMonth());
		return Optional.of(datePeriodClosure.start());
	}
	@Override
	public List<DailyInterimRemainMngDataAndFlg> lstRemainData(String cid, String sid, DatePeriod datePeriod,
			ReferenceAtr referenceAtr) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		List<DailyInterimRemainMngDataAndFlg> lstOutputData = new ArrayList<>();
		//社員に対応する締め開始日を取得する
		Optional<GeneralDate> startDateOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, sid);
		if(!startDateOpt.isPresent()) {
			return lstOutputData;
		}
		GeneralDate startDate = startDateOpt.get();

		//参照先区分を判断
		if(referenceAtr == ReferenceAtr.RECORD) {
			//日別実績を取得 INPUT．期間．開始日 <= 年月日 <= INPUT．期間．終了日
			lstOutputData = this.getAnnualHolidayRemainData(require, cacheCarrier, cid, sid, datePeriod);
		} else {
			//INPUT．開始と締め開始日を比較
			if(datePeriod.start().before(startDate)) {
				//日別実績を取得 INPUT．期間．開始日 <= 年月日 <= 取得した締め開始日 ー 1日
				lstOutputData = this.getAnnualHolidayRemainData(require, cacheCarrier, cid, sid, new DatePeriod(datePeriod.start(), startDate.addDays(-1)));
			}
			//暫定年休管理データを取得 締め開始日 <= 対象日 < INPUT．期間．終了日
			List<TmpAnnualHolidayMng> lstTmpAnnual = annualRepository.getBySidPeriod(sid, new DatePeriod(startDate, datePeriod.end()));
			for (TmpAnnualHolidayMng x : lstTmpAnnual) {
				Optional<InterimRemain> interimInfor = interimRepo.getById(x.getAnnualId());
				if(interimInfor.isPresent()) {
					DailyInterimRemainMngData remainMng = new DailyInterimRemainMngData();
					remainMng.setRecAbsData(Arrays.asList(interimInfor.get()));
					remainMng.setAnnualHolidayData(Optional.of(x));
					DailyInterimRemainMngDataAndFlg outData = new DailyInterimRemainMngDataAndFlg(remainMng, false);
					lstOutputData.add(outData);
				}
			}
		}

		// 年休フレックス補填分の暫定年休管理データを作成
		lstOutputData = getAnnualHolidayInterimFlexTime(sid, datePeriod, lstOutputData);

		lstOutputData = lstOutputData.stream().filter(x -> x.getData().getAnnualHolidayData().isPresent()).collect(Collectors.toList());
		return lstOutputData;
	}
	/**
	 * 年休フレックス補填分の暫定年休管理データを作成
	 * @param sid
	 * @param datePeriod
	 * @param lstOutputData
	 * @return
	 */
	private List<DailyInterimRemainMngDataAndFlg> getAnnualHolidayInterimFlexTime(String sid, DatePeriod datePeriod,
			List<DailyInterimRemainMngDataAndFlg> lstOutputData) {
		// 「月別実績の勤怠時間」を取得
		val attendanceTimes = this.attendanceTimeOfMonthlyRepo.findByPeriodIntoEndYmd(sid, datePeriod);
		List<DailyInterimRemainMngData> lstFlex = new ArrayList<>();
		for (val attendanceTime : attendanceTimes){

			// 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
			val compensFlexOpt = CreateInterimAnnualMngData.ofCompensFlex(
					attendanceTime, attendanceTime.getDatePeriod().end());
			if (compensFlexOpt.isPresent()) {
				lstFlex.add(compensFlexOpt.get());
			}
		}
		for (DailyInterimRemainMngData x : lstFlex) {
			if(!x.getAnnualHolidayData().isPresent()
					|| x.getAnnualHolidayData().get().getUseDays().v() <= 0) {
				continue;
			}
			TmpAnnualHolidayMng annualInterim = x.getAnnualHolidayData().get();
			double useDays = annualInterim.getUseDays().v();
			if(useDays <= 1.0) {
				lstOutputData.add(new DailyInterimRemainMngDataAndFlg(x, true));
				continue;
			}
			for(double i = 0; useDays - i > 0; i++) {
				DailyInterimRemainMngData flexTmp = new DailyInterimRemainMngData();
				flexTmp.setRecAbsData(x.getRecAbsData());
				TmpAnnualHolidayMng annualInterimTmp = new TmpAnnualHolidayMng();
				annualInterimTmp.setAnnualId(annualInterim.getAnnualId());
				annualInterimTmp.setWorkTypeCode(annualInterim.getWorkTypeCode());
				if(useDays - i >= 1) {
					annualInterimTmp.setUseDays(new UseDay(1.0));
				} else {
					annualInterimTmp.setUseDays(new UseDay(0.5));
				}
				flexTmp.setAnnualHolidayData(Optional.of(annualInterimTmp));

				lstOutputData.add(new DailyInterimRemainMngDataAndFlg(flexTmp, true));
			}
		}
		return lstOutputData;
	}

	private List<DailyInterimRemainMngDataAndFlg> getAnnualHolidayRemainData(
			RecordDomRequireService.Require require, CacheCarrier cacheCarrier,
			String cid, String sid, DatePeriod datePeriod) {

		List<DailyInterimRemainMngDataAndFlg> lstOutputData = new ArrayList<>();

		//Workを考慮した月次処理用の暫定残数管理データを作成する
		Map<GeneralDate, DailyInterimRemainMngData> mapRemainData = AggregateMonthlyRecordService
				.mapInterimRemainData(require, cacheCarrier, cid, sid, datePeriod);
		if(mapRemainData != null) {
			for (DailyInterimRemainMngData y : mapRemainData.values()) {
				if(y.getAnnualHolidayData().isPresent()) {
					DailyInterimRemainMngDataAndFlg outData = new DailyInterimRemainMngDataAndFlg(y, true);
					lstOutputData.add(outData);
				}
			}
		}
		return lstOutputData;
	}

	@Override
	public List<AnnualLeaveGrantRemainingData> lstRemainHistory(String sid,
			List<AnnualLeaveGrantRemainingData> lstAnnRemainHis, GeneralDate ymd) {
		//付与時点の残数履歴データを取得
		List<AnnualLeaveTimeRemainingHistory> annTimeData = annTimeRemainHisRepo.findBySid(sid, ymd);
		if(annTimeData.isEmpty()) {
			return lstAnnRemainHis;
		}
		List<AnnualLeaveTimeRemainingHistory> maxDateAnnRemainHis = annTimeData
				.stream().filter(x -> x.getGrantProcessDate().equals(annTimeData.get(0).getGrantProcessDate())).collect(Collectors.toList());
		lstAnnRemainHis.stream().forEach(y -> {
			maxDateAnnRemainHis.stream().forEach(z -> {
				if(y.getGrantDate().equals(z.getGrantDate())) {
					//年休付与残数履歴データ．使用数から、付与時点の使用数を減算
					double useDay = y.getDetails().getUsedNumber().getDays().v() - z.getDetails().getUsedNumber().getDays().v();
					y.getDetails().setUsedNumber(new AnnualLeaveUsedNumber(useDay, null, null));
					//付与数から計算した使用数を減算
					double grantDays = y.getDetails().getGrantNumber().getDays().v() - z.getDetails().getUsedNumber().getDays().v();
					((AnnualLeaveNumberInfo) y.getDetails()).setGrantNumber(AnnualLeaveGrantNumber.createFromJavaType(grantDays, 0));
				}
			});

		});
		return lstAnnRemainHis;
	}

	@Override
	public List<AnnualHolidayGrant> grantInforFormPeriod(String sid, YearMonth ym,  ClosureId closureID,
			ClosureDate closureDate, DatePeriod period, boolean isPastMonth) {
		List<AnnualHolidayGrant> lstOutput = new ArrayList<>();
		//INPUT．過去月集計モードを確認
		if(isPastMonth) {
			//ドメインモデル「年休付与残数履歴データ」を取得
			List<AnnualLeaveRemainingHistory> lstHisAnnInfo = annualRepo.getInfoByExpStatus(sid, ym, closureID, closureDate,
					LeaveExpirationStatus.EXPIRED, period);
			lstHisAnnInfo.stream().forEach(x -> {
				AnnualHolidayGrant data = new AnnualHolidayGrant(x.getGrantDate(), x.getDetails().getGrantNumber().getDays().v(),
						x.getDetails().getUsedNumber().getDays().v(), x.getDetails().getRemainingNumber().getDays().v());
				lstOutput.add(data);
			});

		} else {
			//ドメインモデル「年休付与残数データ」を取得
			List<AnnualLeaveGrantRemainingData> lstAnnLeaRem = annLeaRemRepo.findByExpStatus(sid, LeaveExpirationStatus.EXPIRED, period);
			lstAnnLeaRem.stream().forEach(x -> {
				AnnualHolidayGrant data = new AnnualHolidayGrant(x.getGrantDate(), x.getDetails().getGrantNumber().getDays().v(),
						x.getDetails().getUsedNumber().getDays().v(), x.getDetails().getRemainingNumber().getDays().v());
				lstOutput.add(data);
			});
		}

		return lstOutput;
	}

}
