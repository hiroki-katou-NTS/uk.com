package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updatereserveannual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
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
	public static AtomTask updateReservedAnnualLeaveRemainNumber(RequireM5 require, AggrResultOfReserveLeave output,
			AggrPeriodEachActualClosure period, String empId) {
		
		return deleteDataAfterCurrentMonth(require, period, empId)
				.then(updateNumberOfRemainingLeaveData(require, output, period, empId));
//		} else {
//			return;
//		}
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
		List<AtomTask> atomTask = new ArrayList<>();
		
		String cid = AppContexts.user().companyId();
		List<ReserveLeaveGrantRemainingData> listData = require.reserveLeaveGrantRemainingData(empId, cid);
		if (!listData.isEmpty()) {
			for (ReserveLeaveGrantRemainingData data : listData) {
				ReserveLeaveGrantRemainHistoryData hist = new ReserveLeaveGrantRemainHistoryData(data,
						period.getYearMonth(), period.getClosureId(), period.getClosureDate());
				
				atomTask.add(AtomTask.of(() -> require.addOrUpdateReserveLeaveGrantRemainHistoryData(hist, cid)));
			}
		}

		atomTask.add(updateProcess(require, output.getAsOfPeriodEnd()));
		
		atomTask.add(updateRsvLeaveTimeRemainHistProcess(require, output.getAsOfGrant().orElse(Collections.emptyList())));

		return AtomTask.bundle(atomTask);
	}

	/**
	 * 積立年休付与残数データ更新処理
	 * 
	 * @param ReserveLeaveInfo
	 */
	private static AtomTask updateProcess(RequireM3 require, ReserveLeaveInfo info) {
		List<AtomTask> atomTasks = new ArrayList<>();
		
		String cId = AppContexts.user().companyId();
		List<ReserveLeaveGrantRemainingData> listData = info.getGrantRemainingNumberList();
		for (ReserveLeaveGrantRemainingData data : listData) {
			List<ReserveLeaveGrantRemainingData> lstDomain = require.reserveLeaveGrantRemainingData(data.getEmployeeId(),
					data.getGrantDate());
			boolean found = false;
			for (ReserveLeaveGrantRemainingData d : lstDomain) {
				if (data.getRsvLeaID().equals(d.getRsvLeaID())) {
					// update
					atomTasks.add(AtomTask.of(() -> require.updateReserveLeaveGrantRemainingData(data)));
					found = true;
					break;
				}
			}
			if (!found) {
				// insert
				ReserveLeaveGrantRemainingData addDomain = ReserveLeaveGrantRemainingData.createFromJavaType(
						(data.getRsvLeaID() != null && !data.getRsvLeaID().isEmpty()) ? data.getRsvLeaID()
								: IdentifierUtil.randomUniqueId(),
						data.getEmployeeId(), data.getGrantDate(), data.getDeadline(), data.getExpirationStatus().value,
						data.getRegisterType().value, data.getDetails().getGrantNumber().v(),
						data.getDetails().getUsedNumber().getDays().v(),
						data.getDetails().getUsedNumber().getOverLimitDays().isPresent()
								? data.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
						data.getDetails().getRemainingNumber().v());
				
				atomTasks.add(AtomTask.of(() -> require.addReserveLeaveGrantRemainingData(addDomain, cId)));
			}
		}
		
		return AtomTask.bundle(atomTasks);
	}

	/**
	 * 積休付与時点残数履歴データ更新処理
	 * 
	 * @param listInfo
	 */
	private static AtomTask updateRsvLeaveTimeRemainHistProcess(RequireM2 require, List<ReserveLeaveInfo> listInfo) {
		List<AtomTask> atomTask = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		
		for (ReserveLeaveInfo info : listInfo) {
			List<ReserveLeaveGrantRemainingData> listData = info.getGrantRemainingNumberList();
			for (ReserveLeaveGrantRemainingData data : listData) {
				ReserveLeaveGrantTimeRemainHistoryData hist = new ReserveLeaveGrantTimeRemainHistoryData(data,
						info.getYmd());
				
				atomTask.add(AtomTask.of(() -> require.addOrUpdateReserveLeaveGrantTimeRemainHistoryData(hist, cid)));
			}

		}
		
		return AtomTask.bundle(atomTask);
	}

	/**
	 * 当月以降の積休付与残数データを削除
	 */
	private static AtomTask deleteDataAfterCurrentMonth(RequireM1 require, AggrPeriodEachActualClosure period, String empId) {
		
		return AtomTask.of(() -> {
			require.deleteReserveLeaveGrantRemainingData(empId, period.getPeriod().start());
			require.deleteReserveLeaveGrantRemainHistoryData(empId, period.getYearMonth(), period.getClosureId(), period.getClosureDate());
			require.deleteReserveLeaveGrantTimeRemainHistoryData(empId, period.getPeriod().start());
		});
	}

	public static interface RequireM5 extends RequireM1, RequireM4 {
		
	}
	
	public static interface RequireM4 extends RequireM2, RequireM3 {
		
		void addOrUpdateReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainHistoryData domain, String cid);
		
		List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId, String cId);
	}
	
	public static interface RequireM3 {
		
		List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId, GeneralDate grantDate);
		
		void updateReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data);
		
		void addReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data, String cId);
	}
	
	public static interface RequireM2 {
		
		void addOrUpdateReserveLeaveGrantTimeRemainHistoryData(ReserveLeaveGrantTimeRemainHistoryData domain, String cid);
	}
	
	public static interface RequireM1 {
		
		void deleteReserveLeaveGrantRemainingData(String employeeId, GeneralDate date);
		
		void deleteReserveLeaveGrantRemainHistoryData(String employeeId, YearMonth ym, ClosureId closureId, ClosureDate closureDate);
		
		void deleteReserveLeaveGrantTimeRemainHistoryData(String employeeId, GeneralDate date);
	}

}
