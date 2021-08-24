package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedRemainDataForMonthlyAgg;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * @author HungTT - <<Work>> 代休処理
 *
 */
public class CompensatoryHolidayProcess {

	/**
	 * 代休処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public static AtomTask compensatoryHolidayProcess(Require require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId, FixedRemainDataForMonthlyAgg remainDataMonthAgg) {

		/** 代休残数計算 */
		val output = calculateRemainCompensatory(require, cacheCarrier, period, empId, remainDataMonthAgg);

		/** 代休残数更新 */
		return updateRemainCompensatoryHoliday(require, output.getVacationDetails(), period, empId)
				/** 代休逐次休暇の紐付け情報を追加する */
				.then(addSeaCompensatory(require, empId, output.getLstSeqVacation()))
				/** 代休暫定データ削除 */
				.then(deleteTemp(require, empId, period.getPeriod()));
	}

	/** 代休逐次休暇の紐付け情報を追加する */
	private static AtomTask addSeaCompensatory(Require1 require, String sid, List<SeqVacationAssociationInfo> lstSeqVacation) {

		/** 「パラメータ。List<休出代休明細>」をループする */
		val atomTasks = lstSeqVacation.stream().map(c -> {

			/** 休出代休紐付け管理を作成する */
			val domain = new LeaveComDayOffManagement(sid, c);

			/** 休出代休紐付け管理をインサートする */
			return AtomTask.of(() -> require.addLeaveComDayOffManagement(domain));
		}).collect(Collectors.toList());

		return AtomTask.bundle(atomTasks);
	}


	/** 代休残数更新 */
	private static AtomTask updateRemainCompensatoryHoliday(RequireM5 require,
			VacationDetails vacationDetails, AggrPeriodEachActualClosure period, String empId) {

		String companyId = AppContexts.user().companyId();

		return AtomTask.of(() -> {})
				/** アルゴリズム「当月以降の休出管理データを削除」を実行する */
				.then(() -> require.deleteLeaveManagementDataAfter(empId, false, period.getPeriod().start()))
				/** アルゴリズム「休出管理データの更新」を実行する */
				.then(updateLeaveMngData(require, companyId, vacationDetails.getLstAcctAbsenDetail()))
				/** アルゴリズム「当月以降の代休管理データを削除」を実行する */
				.then(() -> require.deleteCompensatoryDayOffManaDataAfter(empId, false, period.getPeriod().start()))
				/** アルゴリズム「代休管理データの更新」を実行する */
				.then(updateCompensatoryDayData(require, companyId, vacationDetails.getLstAcctAbsenDetail()));
	}

	/** 休出管理データの更新 */
	private static AtomTask updateLeaveMngData(RequireM2 require, String companyId, List<AccumulationAbsenceDetail> lstDetailData) {

		List<AtomTask> atomTask = lstDetailData.stream()
				.filter(a -> a.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.map(c -> {

					val data = (UnbalanceVacation) c;

					/** ドメインモデル「休出管理データ」を取得する */
					return require.leaveManagementData(data.getManageId()).map(lmd -> {

						/** 取得したドメインモデル「休出管理データ」を更新する */
						lmd.update(data);
						return AtomTask.of(() -> require.updateLeaveManagementData(lmd));
					}).orElseGet(() -> {

						/** ドメインモデル「休出管理データ」を追加する */
						return AtomTask.of(() -> require.createLeaveManagementData(LeaveManagementData.of(companyId, data)));
					});
				})
				.collect(Collectors.toList());

		return AtomTask.bundle(atomTask);
	}

	/** アルゴリズム「代休管理データの更新」を実行する */
	private static AtomTask updateCompensatoryDayData(RequireM4 require, String companyId, List<AccumulationAbsenceDetail> lstDetailData) {

		List<AtomTask> atomTask = lstDetailData.stream()
				.filter(a -> a.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				.map(data -> {

					/** ドメインモデル「代休管理データ」を取得する */
					return require.compensatoryDayOffManaData(data.getManageId()).map(cdm -> {

						/** 取得したドメインモデル「代休管理データ」を更新する */
						cdm.update(data);
						return AtomTask.of(() -> require.updateCompensatoryDayOffManaData(cdm));
					}).orElseGet(() -> {

						/** ドメインモデル「代休管理データ」を追加する */
						return AtomTask.of(() -> require.createCompensatoryDayOffManaData(CompensatoryDayOffManaData.of(companyId, data)));
					});
				})
				.collect(Collectors.toList());

		return AtomTask.bundle(atomTask);
	}

	/**
	 * 代休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @return 代休の集計結果
	 */
	public static SubstituteHolidayAggrResult calculateRemainCompensatory(RequireM6 require,
			CacheCarrier cacheCarrier, AggrPeriodEachActualClosure period, String empId,
			FixedRemainDataForMonthlyAgg remainDataMonthAgg) {

		String companyId = AppContexts.user().companyId();

		/** 暫定残数データを休出・代休に絞り込む */
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();
		for (val interimRemainMng : remainDataMonthAgg.getDaily()){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			interimMng.addAll(interimRemainMng.getRecAbsData());

			/** 休出 */
			if (interimRemainMng.getBreakData().isPresent()){
				breakMng.add(interimRemainMng.getBreakData().get());
			}
			/** 代休 */
			interimRemainMng.getDayOffData().forEach(c->dayOffMng.add(c));
		}

		/** 期間内の休出代休残数を取得する */
		BreakDayOffRemainMngRefactParam p = new BreakDayOffRemainMngRefactParam(companyId, empId, period.getPeriod(), true,
				period.getPeriod().end(), true, interimMng, Optional.of(CreateAtr.RECORD),
				Optional.of(period.getPeriod()), breakMng, dayOffMng, Optional.empty(), remainDataMonthAgg.getMonthly());

		return NumberRemainVacationLeaveRangeQuery.getBreakDayOffMngInPeriod(require, p);
	}
	
	/**
	 * 暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTemp(Require require, String employeeId, DatePeriod period){
		//暫定休出管理データの削除
		return AtomTask.of(() -> require.deleteInterimBreakMngBySidDatePeriod(employeeId, period))
				//暫定代休管理データの削除
				.then(AtomTask.of(() -> require.deleteInterimDayOffMngBySidDatePeriod(employeeId, period)));
	}
	

	public static interface RequireM6 extends NumberRemainVacationLeaveRangeQuery.Require {

	}

	public static interface RequireM5 extends RequireM2, RequireM4 {

		void deleteLeaveManagementDataAfter(String sid, boolean unknownDateFlag, GeneralDate target);

		void deleteCompensatoryDayOffManaDataAfter(String sid, boolean unknownDateFlag, GeneralDate target);
	}

	public static interface RequireM2 {

		Optional<LeaveManagementData> leaveManagementData(String comDayOffId);

		void updateLeaveManagementData(LeaveManagementData leaveMng);

		void createLeaveManagementData(LeaveManagementData leaveMng);
	}

	public static interface RequireM4 {

		Optional<CompensatoryDayOffManaData> compensatoryDayOffManaData(String comDayOffId);

		void updateCompensatoryDayOffManaData(CompensatoryDayOffManaData domain);

		void createCompensatoryDayOffManaData(CompensatoryDayOffManaData domain);
	}

	public static interface RequireM3 {
		void deleteInterimDayOffMngBySidDatePeriod(String sid, DatePeriod period);
		void deleteInterimBreakMngBySidDatePeriod(String sid, DatePeriod period);
	}

	public static interface Require1 {

		void addLeaveComDayOffManagement(LeaveComDayOffManagement domain);
	}

	public static interface Require extends RequireM6, RequireM5, RequireM3, Require1 {

	}
}
