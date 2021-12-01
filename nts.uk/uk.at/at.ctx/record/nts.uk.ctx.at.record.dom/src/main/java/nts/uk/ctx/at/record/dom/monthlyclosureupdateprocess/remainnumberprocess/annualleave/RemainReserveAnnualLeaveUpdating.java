package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 *
 * @author HungTT - <<Work>> 積立年休残数更新
 *
 */
public class RemainReserveAnnualLeaveUpdating {

	/**
	 * 積立年休残数更新
	 *
	 * @param output
	 * @param period
	 * @param empId
	 */
	public static AtomTask updateReservedAnnualLeaveRemainNumber(RequireM5 require, Optional<AggrResultOfReserveLeave> output,
			AggrPeriodEachActualClosure period, String empId) {

		/** アルゴリズム「当月以降の積休付与残数データを削除」を実行する */
		return deleteDataAfterCurrentMonth(require, period, empId)
				/** アルゴリズム「積休付与残数データの更新」を実行する */
				.then(output.map(rev -> updateNumberOfRemainingLeaveData(require, rev, period, empId))
							.orElseGet(() -> AtomTask.none()));
	}

	/**
	 * 積休付与残数データの更新
	 *
	 * @param output
	 * @param period
	 * @param empId
	 */
	private static AtomTask updateNumberOfRemainingLeaveData(RequireM4 require,
			AggrResultOfReserveLeave output, AggrPeriodEachActualClosure period, String empId) {

		String cid = AppContexts.user().companyId();
		
		/** ドメインモデル「積立年休付与残数データ」を取得する */
		val atomTask = require.reserveLeaveGrantRemainingData(empId).stream().map(data -> {
			
			/** ドメインモデル「積立年休付与残数履歴データ」を追加する */
			ReserveLeaveGrantRemainHistoryData hist = new ReserveLeaveGrantRemainHistoryData(data,
					period.getYearMonth(), period.getClosureId(), period.getClosureDate());
			
			return AtomTask.of(() -> require.addOrUpdateReserveLeaveGrantRemainHistoryData(hist, cid));
		}).collect(Collectors.toList());

		return AtomTask.bundle(atomTask)
				/** アルゴリズム「積立年休付与残数データ更新処理」を実行する */
				.then(updateProcess(require, output.getAsOfStartNextDayOfPeriodEnd()))
				/** ドメインモデル「積休付与時点残数履歴データ」を更新する */
				.then(updateRsvLeaveTimeRemainHistProcess(require, output.getAsOfGrant().orElse(Collections.emptyList())));
	}

	/**
	 * 積立年休付与残数データ更新処理
	 *
	 * @param ReserveLeaveInfo
	 */
	private static AtomTask updateProcess(RequireM3 require, ReserveLeaveInfo info) {

		/** 「積立年休情報．付与残数データ」を取得する */
		List<AtomTask> atomTasks = info.getGrantRemainingNumberList().stream().map(data -> {

			/** 残数がマイナスの場合の補正処理 */
			data.getDetails().correctRemainNumbers();
			data.setRegisterType(GrantRemainRegisterType.MONTH_CLOSE);
			
			/** ドメインモデル「積立年休付与残数データ」を取得する */
			return require.reserveLeaveGrantRemainingData(data.getEmployeeId(), data.getGrantDate()).stream()
				.findFirst().map(remainData -> {
					
					remainData.setRegisterType(data.getRegisterType());
					remainData.setDetails(data.getDetails());
					remainData.setExpirationStatus(data.getExpirationStatus());
					
					/** ドメインモデル「積立年休付与残数データ」を更新する */
					return AtomTask.of(() -> require.updateReserveLeaveGrantRemainingData(remainData));
				}).orElseGet(() -> {
					
					/** ドメインモデル「積立年休付与残数データ」を追加する */
					if (StringUtil.isNullOrEmpty(data.getLeaveID(), true)) {
						data.setLeaveID(IdentifierUtil.randomUniqueId());
					}

					/** ドメインモデル「積立年休付与残数データ」を追加する */
					return AtomTask.of(() -> require.addReserveLeaveGrantRemainingData(data));
				});
		}).collect(Collectors.toList());
		
		return AtomTask.bundle(atomTasks);
	}

	/**
	 * 積休付与時点残数履歴データ更新処理
	 *
	 * @param listInfo
	 */
	private static AtomTask updateRsvLeaveTimeRemainHistProcess(RequireM2 require, List<ReserveLeaveInfo> listInfo) {

		/** パラメータ「積立年休情報（List）」を受け取る */
		List<AtomTask> atomTask = listInfo.stream().map(info -> {

			/** 「付与残数データ」を取得する */
			return info.getGrantRemainingNumberList().stream().map(data -> {
				
				/** ドメインモデル「積立年休付付与時点残数履歴データ」を追加する */
				ReserveLeaveGrantTimeRemainHistoryData hist = new ReserveLeaveGrantTimeRemainHistoryData(data, info.getYmd());
				return AtomTask.of(() -> require.addOrUpdateReserveLeaveGrantTimeRemainHistoryData(hist));
			}).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());

		return AtomTask.bundle(atomTask);
	}

	/**
	 * 当月以降の積休付与残数データを削除
	 */
	private static AtomTask deleteDataAfterCurrentMonth(RequireM1 require, AggrPeriodEachActualClosure period, String empId) {

		return AtomTask.of(() -> {
			/** TODO: 一旦コメントアウト　*/
//			require.deleteReserveLeaveGrantRemainingData(empId, period.getPeriod().start());
//			require.deleteReserveLeaveGrantRemainHistoryData(empId, period.getYearMonth(), period.getClosureId(), period.getClosureDate());
//			require.deleteReserveLeaveGrantTimeRemainHistoryData(empId, period.getPeriod().start());
		});
	}

	public static interface RequireM5 extends RequireM1, RequireM4 {

	}

	public static interface RequireM4 extends RequireM2, RequireM3 {

		void addOrUpdateReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainHistoryData domain, String cid);
		
		List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId);
	}

	public static interface RequireM3 {

		List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId, GeneralDate grantDate);

		void updateReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data);

		void addReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data);
	}

	public static interface RequireM2 {

		void addOrUpdateReserveLeaveGrantTimeRemainHistoryData(ReserveLeaveGrantTimeRemainHistoryData domain);
	}

	public static interface RequireM1 {

		void deleteReserveLeaveGrantRemainingData(String employeeId, GeneralDate date);

		void deleteReserveLeaveGrantRemainHistoryData(String employeeId, YearMonth ym, ClosureId closureId, ClosureDate closureDate);

		void deleteReserveLeaveGrantTimeRemainHistoryData(String employeeId, GeneralDate date);
	}

}
