package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.diagnose.stopwatch.concurrent.ConcurrentStopwatches;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCreateDataInputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.GetDaysForCalcAttdRate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.CreateInterimAnnualMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUndigestDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUndigestTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.service.RemainNumberCreateInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.RemainDaysOfSpecialHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveRemainNoMinus;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM4;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM5;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM6;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM7;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM8;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.pererror.CreatePerErrorsFromLeaveErrors;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.RemainDataDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveAttdRateDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUndigestedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.UndigestedAnnualLeaveDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.UndigestedTimeAnnualLeaveTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.DayOffDayAndTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.MonthlyDayoffRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.RemainDataTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveGrant;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveUndigestedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc.RequireM3;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;


/**
 * 処理：ドメインサービス：月別実績を集計する
 * @author masaaki_jinno
 *
 */
@Getter
public class MonthlyAggregationRemainingNumberImpl implements MonthlyAggregationRemainingNumber {

	/** 会社ID */
	@Setter
	private String companyId;
	/** 社員ID */
	@Setter
	private String employeeId;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日 */
	private ClosureDate closureDate;

	/** 月別集計で必要な会社別設定 */
	@Setter
	private MonAggrCompanySettings companySets;
	/** 月別集計で必要な社員別設定 */
	private MonAggrEmployeeSettings employeeSets;

	/** 集計結果 */
	private AggregateMonthlyRecordValue aggregateResult;

	@Setter
	private MonthlyCalculatingDailys monthlyCalculatingDailys;

	/** 暫定残数データ */
	private Map<GeneralDate, DailyInterimRemainMngData> dailyInterimRemainMngs;
	/** 暫定残数データ上書きフラグ */
	private boolean isOverWriteRemain;


	public AggregateMonthlyRecordValue aggregation(
			RequireM8 require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate) {

		this.aggregateResult = new AggregateMonthlyRecordValue();

		ConcurrentStopwatches.start("12405:暫定データ作成：");

		// Workを考慮した月次処理用の暫定残数管理データを作成する
		this.createDailyInterimRemainMngs(require, cacheCarrier, period);

		ConcurrentStopwatches.stop("12405:暫定データ作成：");
		ConcurrentStopwatches.start("12410:年休積休：");

		// 年休、積休
		this.annualAndReserveLeaveRemain(require, cacheCarrier, period, interimRemainMngMode, isCalcAttendanceRate);

		ConcurrentStopwatches.stop("12410:年休積休：");
		ConcurrentStopwatches.start("12420:振休：");

		// 振休
		this.absenceLeaveRemain(require, cacheCarrier, period, interimRemainMngMode);

		ConcurrentStopwatches.stop("12420:振休：");
		ConcurrentStopwatches.start("12430:代休：");

		// 代休
		this.dayoffRemain(require, cacheCarrier, period, interimRemainMngMode);

		ConcurrentStopwatches.stop("12430:代休：");
		ConcurrentStopwatches.start("12440:特別休暇：");

		// 特別休暇
		this.specialLeaveRemain(require, cacheCarrier, period, interimRemainMngMode);

		ConcurrentStopwatches.stop("12440:特別休暇：");

		return this.aggregateResult;
	}

	/**
	 * 年休、積休
	 *
	 * @param period 期間
	 * @param interimRemainMngMode 暫定残数データ管理モード
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 */
	public void annualAndReserveLeaveRemain(RequireM6 require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate) {

		// 暫定残数データを年休・積立年休に絞り込む
		List<TmpAnnualLeaveMngWork> tmpAnnualLeaveMngs = new ArrayList<>();
		List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			val master = dailyInterimRemainMng.getRecAbsData().get(0);

			// 年休
			if (dailyInterimRemainMng.getAnnualHolidayData().isPresent()) {
				val data = dailyInterimRemainMng.getAnnualHolidayData().get();
				tmpAnnualLeaveMngs.add(TmpAnnualLeaveMngWork.of(master, data));
			}

			// 積立年休
			if (dailyInterimRemainMng.getResereData().isPresent()) {
				val data = dailyInterimRemainMng.getResereData().get();
				tmpReserveLeaveMngs.add(TmpReserveLeaveMngWork.of(master, data));
			}
		}

		// 月別実績の計算結果が存在するかチェック
		boolean isOverWriteAnnual = this.isOverWriteRemain;
		if (this.aggregateResult.getAttendanceTime().isPresent()) {

			// 年休控除日数分の年休暫定残数データを作成する
			val compensFlexWorkOpt = CreateInterimAnnualMngData.ofCompensFlexToWork(
					this.aggregateResult.getAttendanceTime().get(), period.end());
			if (compensFlexWorkOpt.isPresent()) {
				tmpAnnualLeaveMngs.add(compensFlexWorkOpt.get());
				isOverWriteAnnual = true;
			}
		}

		// 「モード」をチェック
		CalYearOffWorkAttendRate daysForCalcAttdRate = new CalYearOffWorkAttendRate();
		if (interimRemainMngMode == InterimRemainMngMode.MONTHLY) {

			// 日別実績から出勤率計算用日数を取得 （月別集計用）
			daysForCalcAttdRate = GetDaysForCalcAttdRate.algorithm(require, this.companyId,
					this.employeeId, period, this.companySets, this.monthlyCalculatingDailys);
		}

		// 期間中の年休積休残数を取得
		// Require の不整合によるエラー
		AggrResultOfAnnAndRsvLeave aggrResult = GetAnnAndRsvRemNumWithinPeriod.algorithm(null, cacheCarrier,
				this.companyId, this.employeeId, period, interimRemainMngMode,
				// period.end(), true, isCalcAttendanceRate,
				period.end(), false, isCalcAttendanceRate, Optional.of(isOverWriteAnnual),
				Optional.of(tmpAnnualLeaveMngs), Optional.of(tmpReserveLeaveMngs), Optional.of(false),
				//あとで前回集計結果も渡せるようにする
				//Optional.of(this.employeeSets.isNoCheckStartDate()), this.prevAggrResult.getAnnualLeave(),
				Optional.of(this.employeeSets.isNoCheckStartDate()), Optional.empty(),
				//あとで前回集計結果も渡せるようにする
				//this.prevAggrResult.getReserveLeave(), Optional.of(this.companySets), Optional.of(this.employeeSets),
				Optional.empty(), Optional.of(this.companySets), Optional.of(this.employeeSets),
				Optional.of(this.monthlyCalculatingDailys));

		// 2回目の取得以降は、締め開始日を確認させる
		this.employeeSets.setNoCheckStartDate(false);

		if (aggrResult.getAnnualLeave().isPresent()) {
			val asOfPeriodEnd = aggrResult.getAnnualLeave().get().getAsOfPeriodEnd();
			val asOfStartNextDayOfPeriodEnd = aggrResult.getAnnualLeave().get().getAsOfStartNextDayOfPeriodEnd();
			val remainingNumber = asOfPeriodEnd.getRemainingNumber();

			//未消化数
			AnnualLeaveUndigestedNumber undigestedNumber= new AnnualLeaveUndigestedNumber();
			if(asOfStartNextDayOfPeriodEnd.getRemainingNumber().getAnnualLeaveUndigestNumber().isPresent()) {

				LeaveUndigestDayNumber days = asOfStartNextDayOfPeriodEnd.getRemainingNumber().getAnnualLeaveUndigestNumber().get().getDays();
				Optional<LeaveUndigestTime> minutes = asOfStartNextDayOfPeriodEnd.getRemainingNumber().getAnnualLeaveUndigestNumber().get().getMinutes();

				undigestedNumber=AnnualLeaveUndigestedNumber.of(
						UndigestedAnnualLeaveDays.of(new AnnualLeaveUsedDayNumber(days.v())),
						Optional.of(UndigestedTimeAnnualLeaveTime.of(new UsedMinutes(minutes.get().v())))
						);
			}

			// 年休月別残数データを更新
			AnnLeaRemNumEachMonth annLeaRemNum = AnnLeaRemNumEachMonth.of(this.employeeId, this.yearMonth,
					this.closureId, this.closureDate, period, ClosureStatus.UNTREATED,
					remainingNumber.getAnnualLeaveNoMinus(), remainingNumber.getAnnualLeaveWithMinus(),
					remainingNumber.getHalfDayAnnualLeaveNoMinus(), remainingNumber.getHalfDayAnnualLeaveWithMinus(),
					asOfStartNextDayOfPeriodEnd.getGrantInfo(), remainingNumber.getTimeAnnualLeaveNoMinus(),
					remainingNumber.getTimeAnnualLeaveWithMinus(),
					AnnualLeaveAttdRateDays.of(new MonthlyDays(daysForCalcAttdRate.getWorkingDays()),
							new MonthlyDays(daysForCalcAttdRate.getPrescribedDays()),
							new MonthlyDays(daysForCalcAttdRate.getDeductedDays())),
					asOfStartNextDayOfPeriodEnd.getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent(),
					undigestedNumber);
			this.aggregateResult.getAnnLeaRemNumEachMonthList().add(annLeaRemNum);

			// 年休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors()
					.addAll(CreatePerErrorsFromLeaveErrors.fromAnnualLeave(this.employeeId, this.yearMonth,
							this.closureId, this.closureDate,
							aggrResult.getAnnualLeave().get().getAnnualLeaveErrors()));
		}

		if (aggrResult.getReserveLeave().isPresent()) {
			val asOfPeriodEnd = aggrResult.getReserveLeave().get().getAsOfPeriodEnd();
			val asOfStartNextDayOfPeriodEnd = aggrResult.getReserveLeave().get().getAsOfStartNextDayOfPeriodEnd();
			val remainingNumber = asOfPeriodEnd.getRemainingNumber();

			//未消化数
			ReserveLeaveUndigestedNumber undigestedNumber= new ReserveLeaveUndigestedNumber();

			ReserveLeaveRemainingDayNumber days = asOfStartNextDayOfPeriodEnd.getRemainingNumber().getＲeserveLeaveUndigestedNumber().getUndigestedDays();
			//Optional<LeaveUndigestTime> minutes = asOfStartNextDayOfPeriodEnd.getRemainingNumber().getAnnualLeaveUndigestNumber().get().getMinutes();

			undigestedNumber=ReserveLeaveUndigestedNumber.of(
					new ReserveLeaveRemainingDayNumber(days.v())
					);

			// 積立年休月別残数データを更新
			ReserveLeaveGrant reserveLeaveGrant = null;
			if (asOfStartNextDayOfPeriodEnd.getGrantInfo().isPresent()) {
				reserveLeaveGrant = ReserveLeaveGrant
						.of(asOfStartNextDayOfPeriodEnd.getGrantInfo().get().getGrantDays());
			}
			RsvLeaRemNumEachMonth rsvLeaRemNum = RsvLeaRemNumEachMonth.of(
					this.employeeId, this.yearMonth,
					this.closureId, this.closureDate, period, ClosureStatus.UNTREATED,
					remainingNumber.getReserveLeaveNoMinus(), remainingNumber.getReserveLeaveWithMinus(),
					Optional.ofNullable(reserveLeaveGrant),
					asOfStartNextDayOfPeriodEnd.getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent(),
					undigestedNumber );

			this.aggregateResult.getRsvLeaRemNumEachMonthList().add(rsvLeaRemNum);

			// 積立年休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors()
					.addAll(CreatePerErrorsFromLeaveErrors.fromReserveLeave(this.employeeId, this.yearMonth,
							this.closureId, this.closureDate,
							aggrResult.getReserveLeave().get().getReserveLeaveErrors()));
		}

		// 集計結果を前回集計結果に引き継ぐ
		//this.aggregateResult.setAggrResultOfAnnAndRsvLeave(aggrResult);
	}

	/**
	 * 振休
	 *
	 * @param period 期間
	 * @param interimRemainMngMode 暫定残数データ管理モード
	 */
	public void absenceLeaveRemain(RequireM5 require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode) {

		// 暫定残数データを振休・振出に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimAbsMng> useAbsMng = new ArrayList<>();
		List<InterimRecMng> useRecMng = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			interimMng.addAll(dailyInterimRemainMng.getRecAbsData());

			// 振休
			if (dailyInterimRemainMng.getInterimAbsData().isPresent()) {
				useAbsMng.add(dailyInterimRemainMng.getInterimAbsData().get());
			}

			// 振出
			if (dailyInterimRemainMng.getRecData().isPresent()) {
				useRecMng.add(dailyInterimRemainMng.getRecData().get());
			}
		}

		// 期間内の振休振出残数を取得する
		AbsRecMngInPeriodParamInput paramInput = new AbsRecMngInPeriodParamInput(this.companyId, this.employeeId,
				period, period.end(), (interimRemainMngMode == InterimRemainMngMode.MONTHLY), this.isOverWriteRemain,
				useAbsMng, interimMng, useRecMng, Optional.empty(), Optional.empty(), Optional.empty());
		val aggrResult = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngInPeriod(require, cacheCarrier, paramInput);
		if (aggrResult != null) {

			// 振休月別残数データを更新
			AbsenceLeaveRemainData absLeaRemNum = new AbsenceLeaveRemainData(this.employeeId, this.yearMonth,
					this.closureId.value, this.closureDate.getClosureDay().v(), this.closureDate.getLastDayOfMonth(),
					ClosureStatus.UNTREATED, period.start(), period.end(),
					new RemainDataDaysMonth(aggrResult.getOccurrenceDays()),
					new RemainDataDaysMonth(aggrResult.getUseDays()),
					new AttendanceDaysMonthToTal(aggrResult.getRemainDays()),
					new AttendanceDaysMonthToTal(aggrResult.getCarryForwardDays()),
					new RemainDataDaysMonth(aggrResult.getUnDigestedDays()));
			this.aggregateResult.getAbsenceLeaveRemainList().add(absLeaRemNum);

			// 振休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors().addAll(CreatePerErrorsFromLeaveErrors.fromPause(this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, aggrResult.getPError()));

			// 集計結果を前回集計結果に引き継ぐ
			//this.aggregateResult.setAbsRecRemainMngOfInPeriodOpt(Optional.of(aggrResult));
		}
	}

	/**
	 * 代休
	 *
	 * @param period 期間
	 * @param interimRemainMngMode 暫定残数データ管理モード
	 */
	public void dayoffRemain(RequireM4 require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode) {

		// 暫定残数データを休出・代休に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			interimMng.addAll(dailyInterimRemainMng.getRecAbsData());

			// 休出
			if (dailyInterimRemainMng.getBreakData().isPresent()) {
				breakMng.add(dailyInterimRemainMng.getBreakData().get());
			}

			// 代休
			if (dailyInterimRemainMng.getDayOffData().isPresent()) {
				dayOffMng.add(dailyInterimRemainMng.getDayOffData().get());
			}
		}

		// 期間内の休出代休残数を取得する
		BreakDayOffRemainMngParam inputParam = new BreakDayOffRemainMngParam(this.companyId, this.employeeId, period,
				(interimRemainMngMode == InterimRemainMngMode.MONTHLY), period.end(), this.isOverWriteRemain,
				interimMng, breakMng, dayOffMng, Optional.empty(), Optional.empty(), Optional.empty());
		val aggrResult = BreakDayOffMngInPeriodQuery.getBreakDayOffMngInPeriod(require, cacheCarrier, inputParam);
		if (aggrResult != null) {

			// 代休月別残数データを更新
			MonthlyDayoffRemainData monDayRemNum = new MonthlyDayoffRemainData(this.employeeId, this.yearMonth,
					this.closureId.value, this.closureDate.getClosureDay().v(), this.closureDate.getLastDayOfMonth(),
					ClosureStatus.UNTREATED, period.start(), period.end(),
					new DayOffDayAndTimes(new RemainDataDaysMonth(aggrResult.getOccurrenceDays()),
							Optional.of(new RemainDataTimesMonth(aggrResult.getOccurrenceTimes()))),
					new DayOffDayAndTimes(new RemainDataDaysMonth(aggrResult.getUseDays()),
							Optional.of(new RemainDataTimesMonth(aggrResult.getUseTimes()))),
					new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(aggrResult.getRemainDays()),
							Optional.of(new RemainingMinutes(aggrResult.getRemainTimes()))),
					new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(aggrResult.getCarryForwardDays()),
							Optional.of(new RemainingMinutes(aggrResult.getRemainTimes()))),
					new DayOffDayAndTimes(new RemainDataDaysMonth(aggrResult.getUnDigestedDays()),
							Optional.of(new RemainDataTimesMonth(aggrResult.getUnDigestedTimes()))));
			this.aggregateResult.getMonthlyDayoffRemainList().add(monDayRemNum);

			// 代休エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors().addAll(CreatePerErrorsFromLeaveErrors.fromDayOff(this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, aggrResult.getLstError()));

			// 集計結果を前回集計結果に引き継ぐ
			//this.aggregateResult.setBreakDayOffRemainMngOfInPeriodOpt(Optional.of(aggrResult));
		}
	}

	/**
	 * 特別休暇
	 *
	 * @param period 期間
	 * @param interimRemainMngMode 暫定残数データ管理モード
	 */
	public void specialLeaveRemain(RequireM8 require, CacheCarrier cacheCarrier, DatePeriod period,
			InterimRemainMngMode interimRemainMngMode) {

		// 暫定残数データを特別休暇に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimSpecialHolidayMng> interimSpecialData = new ArrayList<>();
		for (val dailyInterimRemainMng : this.dailyInterimRemainMngs.values()) {
			if (dailyInterimRemainMng.getRecAbsData().size() <= 0)
				continue;
			if (dailyInterimRemainMng.getSpecialHolidayData().size() <= 0)
				continue;
			interimMng.addAll(dailyInterimRemainMng.getRecAbsData());
			interimSpecialData.addAll(dailyInterimRemainMng.getSpecialHolidayData());
		}

		// 「特別休暇」を取得する
		List<SpecialHoliday> specialHolidays = require.specialHoliday(this.companyId);
		for (SpecialHoliday specialHoliday : specialHolidays) {
			Integer specialLeaveCode = specialHoliday.getSpecialHolidayCode().v();

			// 前回集計結果を確認する
			Optional<InPeriodOfSpecialLeaveResultInfor> prevSpecialLeaveResult = Optional.empty();
//			if (this.prevSpecialLeaveResultMap.containsKey(specialLeaveCode)) {
//				prevSpecialLeaveResult = Optional.of(this.prevSpecialLeaveResultMap.get(specialLeaveCode));
//			}

			// マイナスなしを含めた期間内の特別休暇残を集計する
			// 期間内の特別休暇残を集計する
			ComplileInPeriodOfSpecialLeaveParam param = new ComplileInPeriodOfSpecialLeaveParam(this.companyId,
					this.employeeId, period,
					// (interimRemainMngMode == InterimRemainMngMode.MONTHLY),
					// period.end(), specialLeaveCode, true,
					(interimRemainMngMode == InterimRemainMngMode.MONTHLY), period.end(), specialLeaveCode, false,
					this.isOverWriteRemain, interimMng, interimSpecialData);
			InPeriodOfSpecialLeaveResultInfor aggrResult = SpecialLeaveManagementService
					.complileInPeriodOfSpecialLeave(require, cacheCarrier, param);

			SpecialLeaveInfo asOfPeriodEnd　=　aggrResult.getAsOfPeriodEnd();
			SpecialLeaveInfo asOfStartNextDayOfPeriodEnd=aggrResult.getAsOfStartNextDayOfPeriodEnd();


			InPeriodOfSpecialLeave inPeriod = aggrResult.getAggSpecialLeaveResult();

			// マイナスなしの残数・使用数を計算
			RemainDaysOfSpecialHoliday remainDays = inPeriod.getRemainDays();
			SpecialLeaveRemainNoMinus remainNoMinus = new SpecialLeaveRemainNoMinus(remainDays);

			// 特別休暇月別残数データを更新
			SpecialHolidayRemainData speLeaRemNum = SpecialHolidayRemainData.of(this.employeeId, this.yearMonth,
					this.closureId, this.closureDate, period, specialLeaveCode, inPeriod, remainNoMinus);
			this.aggregateResult.getSpecialLeaveRemainList().add(speLeaRemNum);

			// 特別休暇エラーから月別残数エラー一覧を作成する
			this.aggregateResult.getPerErrors()
					.addAll(CreatePerErrorsFromLeaveErrors.fromSpecialLeave(this.employeeId, this.yearMonth,
							this.closureId, this.closureDate, specialLeaveCode, inPeriod.getLstError()));

			// 集計結果を前回集計結果に引き継ぐ
			//this.aggregateResult.getInPeriodOfSpecialLeaveResultInforMap().put(specialLeaveCode, aggrResult);
		}
	}

	/**
	 * Workを考慮した月次処理用の暫定残数管理データを作成する
	 *
	 * @param period
	 *            期間
	 */
	private void createDailyInterimRemainMngs(RequireM7 require, CacheCarrier cacheCarrier, DatePeriod period) {

		// 【参考：旧処理】 月次処理用の暫定残数管理データを作成する
		// this.dailyInterimRemainMngs =
		// this.interimRemOffMonth.monthInterimRemainData(
		// this.companyId, this.employeeId, period);

		// 残数作成元情報(実績)を作成する
		List<RecordRemainCreateInfor> recordRemains = RemainNumberCreateInformation.createRemainInfor(
				this.employeeId,
				this.monthlyCalculatingDailys.getAttendanceTimeOfDailyMap(),
				this.monthlyCalculatingDailys.getWorkInfoOfDailyMap());

		// 指定期間の暫定残数管理データを作成する
		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(this.companyId,
				this.employeeId, period, recordRemains, Collections.emptyList(), Collections.emptyList(), false);
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(this.companyId,
				this.companySets.getAbsSettingOpt(), this.companySets.getDayOffSetting());
		this.dailyInterimRemainMngs = InterimRemainOffPeriodCreateData.createInterimRemainDataMng(require, cacheCarrier,
				inputPara, comHolidaySetting);

		this.isOverWriteRemain = (this.dailyInterimRemainMngs.size() > 0);
	}

	public Map<GeneralDate, DailyInterimRemainMngData> createDailyInterimRemainMngs(
			RequireM7 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			DatePeriod period,
			MonAggrCompanySettings comSetting,
			MonthlyCalculatingDailys dailys){

		MonthlyAggregationRemainingNumberImpl proc= new MonthlyAggregationRemainingNumberImpl();

		proc.setCompanyId(companyId);
		proc.setEmployeeId(employeeId);
		proc.setCompanySets(comSetting);
		proc.setMonthlyCalculatingDailys(dailys);
		proc.createDailyInterimRemainMngs(require,cacheCarrier,period);
		return proc.getDailyInterimRemainMngs();
	}
}
