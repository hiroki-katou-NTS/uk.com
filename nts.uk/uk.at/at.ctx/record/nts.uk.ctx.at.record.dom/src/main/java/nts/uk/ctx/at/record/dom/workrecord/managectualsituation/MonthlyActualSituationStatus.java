package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.actuallock.DetermineActualResultLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.PerformanceType;

/**
 * 月の実績の状況を取得する
 */
@Stateless
public class MonthlyActualSituationStatus {
	/** 実績ロックされているか判定する */
	@Inject
	DetermineActualResultLock lockStatusService;
	
	/**
	 * 月の実績の状況を取得する
	 * @param param 実績状況を取得する
	 * @return «Output» 月の実績の状況
	 */
	public MonthlyActualSituationOutput getMonthlyActualSituationStatus(AcquireActualStatus param){		
		MonthlyActualSituationOutput monthlyResult = new MonthlyActualSituationOutput();
		//社員の締め情報
		monthlyResult.setEmployeeClosingInfo(new EmployeeClosingInfo(param.employeeId, param.getClosureId(), param.getClosureDate(), param.getProcessDateYM(), param.getDuration()));
		//実績ロックされているか判定する
		LockStatus lockStatus = lockStatusService.getDetermineActualLocked(param.getCompanyId(), 
				param.getEmployeeId(), param.getClosureDate(), param.getClosureId(), PerformanceType.MONTHLY);
		monthlyResult.setMonthlyLockStatus(lockStatus);
		//対象の職場が就業確定されているかチェックする
		//TODO 就業確定が対象外のため「未確定」で固定にしてください
		monthlyResult.setEmploymentFixedStatus(EmploymentFixedStatus.PENDING);
		//TODO 対象月の月の承認が済んでいるかチェックする
		monthlyResult.setApprovalStatus(ApprovalStatus.APPROVAL);
		//日別実績が存在しているかチェックする
		DailyActualSituation dailyActualSituation = new DailyActualSituation();
		//TODO 対象外
		//日の実績が存在する＝TRUE　で固定
		dailyActualSituation.setDailyAchievementsExist(true);
		//TODO 対象日の本人確認が済んでいるかチェックする
		dailyActualSituation.setIdentityVerificationCompleted(true);
		//TODO 対象期間に日別実績のエラーが発生しているかチェックする
		dailyActualSituation.setDailyRecordError(true);
		monthlyResult.setDailyActualSituation(dailyActualSituation);
		
		return monthlyResult;		
	}
}
