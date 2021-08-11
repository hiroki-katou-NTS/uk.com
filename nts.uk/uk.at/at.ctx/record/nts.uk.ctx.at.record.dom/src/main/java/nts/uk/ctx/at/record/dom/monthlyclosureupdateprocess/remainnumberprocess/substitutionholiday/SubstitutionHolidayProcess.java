package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedRemainDataForMonthlyAgg;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 振休処理
 *
 */
public class SubstitutionHolidayProcess {
	 
	/**
	 * 振休処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @param fixManaDataMonth 月別確定管理データ
	 */
	public static AtomTask substitutionHolidayProcess(Require require, CacheCarrier cacheCarrier, 
			AggrPeriodEachActualClosure period, String empId,
			FixedRemainDataForMonthlyAgg remainDataMonthAgg) {
		
		/** 振休残数計算 */
		val output = calculateRemainHoliday(require, cacheCarrier, period, empId, remainDataMonthAgg);
		
		return AtomTask.of(() -> {})
				 /** 振休残数更新 */
				.then(updateRemainSubstitutionHoliday(require, output.getVacationDetails(), period, empId))
				/** 振休逐次休暇の紐付け情報を追加する */
				.then(addSeqVacation(require, empId, output.getLstSeqVacation()))
				/** 振休暫定データ削除 */
				.then(deleteTempSubstitutionManagement(require, empId, period.getPeriod()));	
	}
	
	/** 振休逐次休暇の紐付け情報を追加する */
	private static AtomTask addSeqVacation(RequireM1 require, String sid, List<SeqVacationAssociationInfo> lstSeqVacation) {
		
		/** 「パラメータ。List<逐次休暇の紐付け情報>」をループする */
		List<AtomTask> atomTask = lstSeqVacation.stream().map(d -> {
			
			/** 振出振休紐付け管理を作成する */
			PayoutSubofHDManagement domain = PayoutSubofHDManagement.of(sid, d);
			
			/** 振出振休紐付け管理をインサートする */
			return AtomTask.of(() -> require.addPayoutSubofHDManagement(domain));
		}).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTask);
	}
	
	/**
	 * 振休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap List<暫定管理データ>
	 * @param fixManaDataMonth 月別確定管理データ
	 * @return 振休の集計結果
	 */
	private static CompenLeaveAggrResult calculateRemainHoliday(RequireM2 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId, FixedRemainDataForMonthlyAgg remainDataMonthAgg) {
		
		String companyId = AppContexts.user().companyId();
		
		/** 暫定残数データを振休・振出に絞り込む */
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimAbsMng> useAbsMng = new ArrayList<>();
		List<InterimRecMng> useRecMng = new ArrayList<>();
		for (val interimRemainMng : remainDataMonthAgg.getDaily()){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			interimMng.addAll(interimRemainMng.getRecAbsData());

			/** 振休 */
			if (interimRemainMng.getInterimAbsData().isPresent()){
				useAbsMng.add(interimRemainMng.getInterimAbsData().get());
			}
			
			/** 振出 */
			if (interimRemainMng.getRecData().isPresent()){
				useRecMng.add(interimRemainMng.getRecData().get());
			}
		}
		
		/** 「期間内の振出振休残数を取得する」を実行する */
		val param = new AbsRecMngInPeriodRefactParamInput(companyId, empId, period.getPeriod(), period.getPeriod().end(), 
				true, true, useAbsMng, interimMng, useRecMng, Optional.empty(), Optional.of(CreateAtr.RECORD),
				Optional.of(period.getPeriod()), remainDataMonthAgg.getMonthly());
		
		return NumberCompensatoryLeavePeriodQuery.process(require, param);
	}		

	/** 振休残数更新 */
	private static AtomTask updateRemainSubstitutionHoliday(RequireM5 require, VacationDetails vacationDetails,
			AggrPeriodEachActualClosure period, String empId) {
		
		String companyId = AppContexts.user().companyId();
		
		/**　当月以降の振出管理データを削除　*/
		return deletePayoutManaData(require, period.getPeriod(), empId)
				/**　アルゴリズム「振出管理データの更新」を実行する　*/
				.then(updatePayoutMngData(require, companyId, vacationDetails.getLstAcctAbsenDetail()))
				/**　アルゴリズム「当月以降の振休管理データを削除」を実行する　*/
				.then(deleteSubManaData(require, period.getPeriod(), empId))
				/**　アルゴリズム「振休管理データの更新」を実行する　*/
				.then(updateSubstitutionHolidayMngData(require, companyId, vacationDetails.getLstAcctAbsenDetail()));
	}
	
	
	/**
	 * 振休暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempSubstitutionManagement(Require require, String employeeId, DatePeriod period){
		return AtomTask.of(() -> require.deleteInterimAbsMngBySidDatePeriod(employeeId, period))
				.then(AtomTask.of(() -> require.deleteInterimRecMngBySidDatePeriod(employeeId, period)));
	}
	
	
	/**　当月以降の振出管理データを削除　*/
	private static AtomTask deletePayoutManaData(RequireM4 require, DatePeriod period, String empId){

		/** ドメインモデル「振出管理データ」を削除する */
		return AtomTask.of(() -> require.deletePayoutManagementDataAfter(empId, false, period.start()));
	}

	/**　アルゴリズム「当月以降の振休管理データを削除」を実行する　*/
	private static AtomTask deleteSubManaData(RequireM6 require, DatePeriod period, String empId){

		/** ドメインモデル「振休管理データ」を削除する */
		return AtomTask.of(() -> require.deleteSubstitutionOfHDManagementDataAfter(empId, false, period.start()));
	}

	/** 振出管理データの更新 */
	private static AtomTask updatePayoutMngData(RequireM7 require, 
			String companyId, List<AccumulationAbsenceDetail> lstAbsRecMng) {
		
		/** パラメータ「List<振出振休明細>」を取得 */
		List<AtomTask> atomTasks = lstAbsRecMng.stream()
				.filter(a -> a.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.map(data -> {
					
					val unbalanceCompensation = (UnbalanceCompensation) data;
					
					/** ドメインモデル「振出管理データ」を取得する */
					return require.payoutManagementData(data.getManageId()).map(pm -> {

						/** 取得したドメインモデル「振出管理データ」を更新する */
						pm.update(unbalanceCompensation);
						return AtomTask.of(() -> require.updatePayoutManagementData(pm));
					}).orElseGet(() -> {

						/** ドメインモデル「振出管理データ」を追加する */
						return AtomTask.of(() -> require.createPayoutManagementData(PayoutManagementData.create(companyId, unbalanceCompensation)));
					});
				}).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTasks);
	}

	/** 振休管理データの更新 */
	private static AtomTask updateSubstitutionHolidayMngData(RequireM8 require, 
			String companyId, List<AccumulationAbsenceDetail> lstAbsRecMng) {
		
		if (CollectionUtil.isEmpty(lstAbsRecMng))
			return AtomTask.none();
		
		/** パラメータ「List<逐次発生の休暇明細>」を取得 */
		List<AtomTask> atomTasks = lstAbsRecMng.stream()
				.filter(a -> a.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				.map(data -> {

					val unbalanceCompensation = (AccumulationAbsenceDetail) data;
					
					/** ドメインモデル「振休管理データ」を取得する */
					return require.substitutionOfHDManagementData(unbalanceCompensation.getManageId()).map(sub -> {
						
						/** 取得したドメインモデル「振休管理データ」を更新する */
						sub.update(unbalanceCompensation);
						
						/** 【大塚モードの場合】 */
						ootsukaCorrection(sub);
						
						return AtomTask.of(() -> require.updateSubstitutionOfHDManagementData(sub));
					}).orElseGet(() -> {
						
						/** ドメインモデル「振休管理データ」を追加する */
						val substitutionData = SubstitutionOfHDManagementData.create(companyId, unbalanceCompensation);

						/** 【大塚モードの場合】 */
						ootsukaCorrection(substitutionData);
						
						return AtomTask.of(() -> require.createSubstitutionOfHDManagementData(substitutionData));
					});
				}).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTasks);
	}

	/** 【大塚モードの場合】 */
	private static void ootsukaCorrection(SubstitutionOfHDManagementData sub) {
		
		if (AppContexts.optionLicense().customize().ootsuka()) {

			/** 大塚カスタマイズ　振休残数がマイナスの場合でも、常にクリアする。 */
			sub.setRemainsDay(0d);
		}
	}

	public static interface RequireM5 extends RequireM8, RequireM7, RequireM6, RequireM4 {}
	
	public static interface RequireM4 {
		
		void deletePayoutManagementDataAfter(String sid, boolean unknownDateFlag, GeneralDate target);
	}
	
	public static interface RequireM6 {
		
		void deleteSubstitutionOfHDManagementDataAfter(String sid, boolean unknownDateFlag, GeneralDate target);
	}
	
	public static interface RequireM7 {
		
		Optional<PayoutManagementData> payoutManagementData(String Id);
		
		void updatePayoutManagementData(PayoutManagementData domain);
		
		void createPayoutManagementData(PayoutManagementData domain);
	}
	
	public static interface RequireM8 {
		
		Optional<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String Id);
		
		void updateSubstitutionOfHDManagementData(SubstitutionOfHDManagementData domain);
		
		void createSubstitutionOfHDManagementData(SubstitutionOfHDManagementData domain);
	}

	private static interface RequireM3{
		void deleteInterimAbsMngBySidDatePeriod(String sId, DatePeriod period);
		void deleteInterimRecMngBySidDatePeriod(String sId, DatePeriod period);
	}
	
	public static interface RequireM2 extends NumberCompensatoryLeavePeriodQuery.Require {
		
	}
	
	public static interface Require extends RequireM2, RequireM3, RequireM5, RequireM1 {
		
	}
	
	public static interface RequireM1 {
		
		void addPayoutSubofHDManagement(PayoutSubofHDManagement domain);
	}
}
