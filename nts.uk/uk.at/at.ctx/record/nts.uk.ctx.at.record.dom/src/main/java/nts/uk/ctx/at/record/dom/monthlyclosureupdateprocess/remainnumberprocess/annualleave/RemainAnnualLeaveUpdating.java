package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;

/**
 * 
 * @author HungTT - <<Work>> 年休残数更新
 *
 */


public class RemainAnnualLeaveUpdating {
	
	/**
	 * 年休残数更新
	 * 
	 * @param output
	 * @param period
	 * @param empId
	 */
	public static AtomTask updateRemainAnnualLeave(RequireM5 require, String cid, Optional<AggrResultOfAnnualLeave> output, AggrPeriodEachActualClosure period,
			String empId) {
		
		/** 「年休情報OUTPUT」が存在するかチェック */
		return output.map(annunal -> {
			
			/** アルゴリズム「年休付与残数データの更新」を実行する */
			return updateRemainAnnualLeaveNumber(require, cid, annunal, period, empId)
					/** アルゴリズム「年休上限データの更新」を実行する */
					.then(updateMaxAnnualLeaveNumber(require, annunal, period, empId));
		}).orElseGet(() -> AtomTask.none());
	}

	/**
	 * 年休付与残数データの更新
	 * 
	 * @param output
	 * @param period
	 * @param empId
	 */
	private static AtomTask updateRemainAnnualLeaveNumber(RequireM4 require, String cid, 
			AggrResultOfAnnualLeave output, AggrPeriodEachActualClosure period, String empId) {
		
		/** ドメインモデル「年休付与残数データ」を取得する */
		List<AnnualLeaveGrantRemainingData> listRemainData = require.annualLeaveGrantRemainingData(empId);
		
		List<AtomTask> atomTasks = listRemainData.stream().map(data -> {
			
			/** ドメインモデル「年休付与残数履歴データ」を追加する */
			AnnualLeaveRemainingHistory hist = new AnnualLeaveRemainingHistory(data, period.getYearMonth(),
					period.getClosureId(), period.getClosureDate());

			return AtomTask.of(() -> require.addOrUpdateAnnualLeaveRemainingHistory(hist));
		}).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTasks)
				/** アルゴリズム「年休付与残数データ更新処理」を実行する */
				.then(updateAnnualLeaveRemainProcess(require, output.getAsOfStartNextDayOfPeriodEnd()))
				/** ドメインモデル「年休付与時点残数履歴データ」を更新する */
				.then(updateAnnualLeaveTimeRemainProcess(require, cid, output.getAsOfGrant().orElse(Collections.emptyList())));
	}

	/**
	 * 年休上限データの更新
	 * 
	 * @param output
	 * @param period
	 * @param empId
	 */
	private static AtomTask updateMaxAnnualLeaveNumber(RequireM3 require, 
			AggrResultOfAnnualLeave output, AggrPeriodEachActualClosure period, String empId) {
		
		/** パラメータ「年休上限管理」を受け取る */
		AnnualLeaveMaxData maxDataOutput = output.getAsOfStartNextDayOfPeriodEnd().getMaxData();
		
		/** ドメインモデル「年休上限データ」を取得する */
		return require.annualLeaveMaxData(empId).map(annualMax -> {
			
			AnnualLeaveMaxHistoryData maxDataHist = new AnnualLeaveMaxHistoryData(annualMax, period.getYearMonth(),
					period.getClosureId(), period.getClosureDate());

			/** ドメインモデル「年休上限履歴データ」を追加する */
			return AtomTask.of(() -> require.addOrUpdateAnnualLeaveMaxHistoryData(maxDataHist))
					/** ドメインモデル「年休上限データ」を更新する */
							.then(() -> require.updateAnnualLeaveMaxData(maxDataOutput));
		}).orElseGet(() -> {
			
			/** ドメインモデル「年休上限データ」を追加する */
			return AtomTask.of(() -> require.addAnnualLeaveMaxData(maxDataOutput));
		});
	}

	/** 年休付与残数データ更新処理 */
	private static AtomTask updateAnnualLeaveRemainProcess(RequireM2 require, AnnualLeaveInfo info) {
		
		/** 「年休情報．付与残数データ」を取得する */
		List<AtomTask> atomTask = info.getGrantRemainingNumberList().stream().map(data -> {

			/** 残数がマイナスの場合の補正処理 */
			data.getDetails().correctRemainNumbers();
			data.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
			
			/** ドメインモデル「年休付与残数データ」を取得する */
			return require.annualLeaveGrantRemainingData(data.getEmployeeId(), data.getGrantDate()).stream()
				.findFirst().map(remainData -> {
					
					/** ドメインモデル「年休付与残数データ」を更新する */
					remainData.setRegisterType(data.getRegisterType());
					remainData.setExpirationStatus(data.getExpirationStatus());
					remainData.setDetails(data.getDetails());
					return AtomTask.of(() -> require.updateAnnualLeaveGrantRemainingData(remainData));
				}).orElseGet(() -> {
					
					/** ドメインモデル「年休付与残数データ」を追加する */
					return AtomTask.of(() -> require.addAnnualLeaveGrantRemainingData(data));
				});
		}).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTask);
	}

	/** 年休付与時点残数履歴データ更新処理 */
	private static AtomTask updateAnnualLeaveTimeRemainProcess(RequireM1 require, String cid,  List<AnnualLeaveInfo> listInfor) {
		
		/** パラメータ「年休情報（List）」を受け取る */
		List<AtomTask> atomTask = listInfor.stream().map(infor -> {
			
			/** 「付与残数データ」を取得する */
			return infor.getGrantRemainingNumberList().stream().map(data -> {
				
				/** ドメインモデル「年休付与時点残数履歴データ」を追加する */
				AnnualLeaveTimeRemainingHistory hist = new AnnualLeaveTimeRemainingHistory(data, infor.getYmd());
				return AtomTask.of(() -> require.addOrUpdateAnnualLeaveTimeRemainingHistory(hist));
			}).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTask);
	}

	

	public static interface RequireM5 extends RequireM4, RequireM3 {
	}

	public static interface RequireM4 extends RequireM2, RequireM1 {

		List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId);
		
		void addOrUpdateAnnualLeaveRemainingHistory(AnnualLeaveRemainingHistory domain);
	}
	
	public static interface RequireM3 {
		
		Optional<AnnualLeaveMaxData> annualLeaveMaxData(String employeeId);
		
		void addOrUpdateAnnualLeaveMaxHistoryData(AnnualLeaveMaxHistoryData domain);
		
		void updateAnnualLeaveMaxData(AnnualLeaveMaxData maxData);
		
		void addAnnualLeaveMaxData(AnnualLeaveMaxData maxData);
	}
	
	public static interface RequireM2 {
		
		void addAnnualLeaveGrantRemainingData(AnnualLeaveGrantRemainingData data);
		
		void updateAnnualLeaveGrantRemainingData(AnnualLeaveGrantRemainingData data);
		
		List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId, GeneralDate grantDate);
	}
	
	public static interface RequireM1 {
		
		void addOrUpdateAnnualLeaveTimeRemainingHistory(AnnualLeaveTimeRemainingHistory domain);
	}
}
