package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updateremainnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;

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
	public static AtomTask updateRemainAnnualLeave(RequireM5 require, AggrResultOfAnnualLeave output, AggrPeriodEachActualClosure period,
			String empId) {
//		deleteDataAfterCurrentMonth(period, empId);
//		if (output != null) {
			return updateRemainAnnualLeaveNumber(require, output, period, empId)
					.then(updateMaxAnnualLeaveNumber(require, output, period, empId));
//		} else {
//			return;
//		}
	}

	/**
	 * 年休付与残数データの更新
	 * 
	 * @param output
	 * @param period
	 * @param empId
	 */
	private static AtomTask updateRemainAnnualLeaveNumber(RequireM4 require, 
			AggrResultOfAnnualLeave output, AggrPeriodEachActualClosure period, String empId) {
		
		List<AtomTask> atomTasks = new ArrayList<>();
		
		List<AnnualLeaveGrantRemainingData> listRemainData = require.annualLeaveGrantRemainingData(empId);
		for (AnnualLeaveGrantRemainingData data : listRemainData) {
			AnnualLeaveRemainingHistory hist = new AnnualLeaveRemainingHistory(data, period.getYearMonth(),
					period.getClosureId(), period.getClosureDate());

			atomTasks.add(AtomTask.of(() -> require.addOrUpdateAnnualLeaveRemainingHistory(hist)));
		}
		
		return AtomTask.bundle(atomTasks)
				.then(updateAnnualLeaveRemainProcess(require, output.getAsOfPeriodEnd()))
				.then(updateAnnualLeaveTimeRemainProcess(require, output.getAsOfGrant().orElse(Collections.emptyList())));
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
		List<AtomTask> atomTasks = new ArrayList<>();
		
		Optional<AnnualLeaveMaxData> optMaxData = require.annualLeaveMaxData(empId);
		if (optMaxData.isPresent()) {
			AnnualLeaveMaxHistoryData maxDataHist = new AnnualLeaveMaxHistoryData(optMaxData.get(), period.getYearMonth(),
					period.getClosureId(), period.getClosureDate());
			
			AnnualLeaveMaxData maxDataOutput = output.getAsOfPeriodEnd().getMaxData();
			AnnualLeaveMaxData maxData = AnnualLeaveMaxData.createFromJavaType(empId,
					maxDataOutput.getHalfdayAnnualLeaveMax() != null
							&& maxDataOutput.getHalfdayAnnualLeaveMax().isPresent()
									? maxDataOutput.getHalfdayAnnualLeaveMax().get().getMaxTimes().v() : null,
					maxDataOutput.getHalfdayAnnualLeaveMax() != null
							&& maxDataOutput.getHalfdayAnnualLeaveMax().isPresent()
									? maxDataOutput.getHalfdayAnnualLeaveMax().get().getUsedTimes().v() : null,
					maxDataOutput.getTimeAnnualLeaveMax() != null && maxDataOutput.getTimeAnnualLeaveMax().isPresent()
							? maxDataOutput.getTimeAnnualLeaveMax().get().getMaxMinutes().v() : null,
					maxDataOutput.getTimeAnnualLeaveMax() != null && maxDataOutput.getTimeAnnualLeaveMax().isPresent()
							? maxDataOutput.getTimeAnnualLeaveMax().get().getUsedMinutes().v() : null);
			
			atomTasks.add(AtomTask.of(() -> require.addOrUpdateAnnualLeaveMaxHistoryData(maxDataHist))
								.then(() -> require.updateAnnualLeaveMaxData(maxData)));
		} else {
			AnnualLeaveMaxData maxDataOutput = output.getAsOfPeriodEnd().getMaxData();
			AnnualLeaveMaxData maxData = AnnualLeaveMaxData.createFromJavaType(empId,
					maxDataOutput.getHalfdayAnnualLeaveMax() != null
							&& maxDataOutput.getHalfdayAnnualLeaveMax().isPresent()
									? maxDataOutput.getHalfdayAnnualLeaveMax().get().getMaxTimes().v() : null,
					maxDataOutput.getHalfdayAnnualLeaveMax() != null
							&& maxDataOutput.getHalfdayAnnualLeaveMax().isPresent()
									? maxDataOutput.getHalfdayAnnualLeaveMax().get().getUsedTimes().v() : null,
					maxDataOutput.getTimeAnnualLeaveMax() != null && maxDataOutput.getTimeAnnualLeaveMax().isPresent()
							? maxDataOutput.getTimeAnnualLeaveMax().get().getMaxMinutes().v() : null,
					maxDataOutput.getTimeAnnualLeaveMax() != null && maxDataOutput.getTimeAnnualLeaveMax().isPresent()
							? maxDataOutput.getTimeAnnualLeaveMax().get().getUsedMinutes().v() : null);

			atomTasks.add(AtomTask.of(() -> require.addAnnualLeaveMaxData(maxData)));
		}
		
		return AtomTask.bundle(atomTasks);
	}

	/**
	 * 年休付与残数データ更新処理
	 * 
	 * @param info
	 */
	private static AtomTask updateAnnualLeaveRemainProcess(RequireM2 require, AnnualLeaveInfo info) {
		List<AtomTask> atomTask = new ArrayList<>();
		
		List<AnnualLeaveGrantRemainingData> listData = info.getGrantRemainingNumberList();
		for (AnnualLeaveGrantRemainingData data : listData) {
			List<AnnualLeaveGrantRemainingData> lstDomain = require.annualLeaveGrantRemainingData(data.getEmployeeId(),
					data.getGrantDate());
			boolean found = false;
			for (AnnualLeaveGrantRemainingData d : lstDomain) {
				if (d.getEmployeeId().equals(data.getEmployeeId()) && d.getGrantDate().equals(data.getGrantDate())) {
					// update
					atomTask.add(AtomTask.of(() -> require.updateAnnualLeaveGrantRemainingData(data)));
					found = true;
					break;
				}
			}
			if (!found) {
				// insert
				AnnualLeaveGrantRemainingData addDomain = AnnualLeaveGrantRemainingData.createFromJavaType(
						(data.getAnnLeavID() != null && !data.getAnnLeavID().isEmpty()) ? data.getAnnLeavID()
								: IdentifierUtil.randomUniqueId(),
						data.getCid(), data.getEmployeeId(), data.getGrantDate(), data.getDeadline(),
						data.getExpirationStatus().value, data.getRegisterType().value,
						data.getDetails().getGrantNumber().getDays().v(),
						data.getDetails().getGrantNumber().getMinutes().isPresent()
								? data.getDetails().getGrantNumber().getMinutes().get().v() : null,
						data.getDetails().getUsedNumber().getDays().v(),
						data.getDetails().getUsedNumber().getMinutes().isPresent()
								? data.getDetails().getUsedNumber().getMinutes().get().v() : null,
						data.getDetails().getUsedNumber().getStowageDays().isPresent()
								? data.getDetails().getUsedNumber().getStowageDays().get().v() : null,
						data.getDetails().getRemainingNumber().getDays().v(),
						data.getDetails().getRemainingNumber().getMinutes().isPresent()
								? data.getDetails().getRemainingNumber().getMinutes().get().v() : null,
						data.getDetails().getUsedPercent().v().doubleValue(),
						data.getAnnualLeaveConditionInfo().isPresent()
								? data.getAnnualLeaveConditionInfo().get().getPrescribedDays().v() : null,
						data.getAnnualLeaveConditionInfo().isPresent()
								? data.getAnnualLeaveConditionInfo().get().getDeductedDays().v() : null,
						data.getAnnualLeaveConditionInfo().isPresent()
								? data.getAnnualLeaveConditionInfo().get().getWorkingDays().v() : null);
				atomTask.add(AtomTask.of(() -> require.addAnnualLeaveGrantRemainingData(addDomain)));
			}
		}
		
		return AtomTask.bundle(atomTask);
	}

	/**
	 * 年休付与時点残数履歴データ更新処理
	 */
	private static AtomTask updateAnnualLeaveTimeRemainProcess(RequireM1 require, 
			List<AnnualLeaveInfo> listInfor) {
		AtomTask atomTask = AtomTask.of(() -> {});
		
		for (AnnualLeaveInfo infor : listInfor) {
			for (AnnualLeaveGrantRemainingData data : infor.getGrantRemainingNumberList()) {
				AnnualLeaveTimeRemainingHistory hist = new AnnualLeaveTimeRemainingHistory(data, infor.getYmd());
				atomTask = atomTask.then(() -> require.addOrUpdateAnnualLeaveTimeRemainingHistory(hist));
			}
		}
		
		return atomTask;
	}

	
	/**
	 * 当月以降の年休付与残数データを削除
	 */
	private void deleteDataAfterCurrentMonth(AggrPeriodEachActualClosure period, String empId) {
//		annLeaveRemainRepo.deleteAfterDate(empId, period.getPeriod().start());
//		annLeaveRemainHistRepo.delete(empId, period.getYearMonth(), period.getClosureId(), period.getClosureDate());
//		annLeaveTimeRemainHistRepo.deleteAfterDate(empId, period.getPeriod().start());
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
