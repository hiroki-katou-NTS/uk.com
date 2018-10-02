package nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.approval.monthly;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.EmpPerformMonthParamImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.ApprovalStatus;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class MonthlyApprovalProcess {
	
	@Inject 
	ApprovalProcessRepository approvalRepo;
	@Inject
	private SharedAffJobtitleHisAdapter affJobTitleAdapter;
	@Inject
	DailyPerformanceAdapter dailyPerformanceAdapter;
	
	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;
	
	/**
	 * 対象月の月の承認が済んでいるかチェックする
	 * @param employeeId: 社員ID
	 * @param processDateYM: 年月
	 * @param closureId: 締め
	 * @param closureDate: 締め日
	 * @return 承認が済んでいる
	 */
	public ApprovalStatus monthlyApprovalCheck(String cId, String employeeId, Integer processDateYM, Integer closureId, GeneralDate closureDate,Optional<ApprovalProcess> approvalProcOp,List<SharedAffJobTitleHisImport> listShareAff){
		//社員が対象月の承認処理を利用できるかチェックする
		if(!canUseMonthlyApprovalCheck(cId, employeeId, closureDate,approvalProcOp,listShareAff)){
			//利用できない場合
			return ApprovalStatus.APPROVAL;
		}
		// [No.533](中間データ版)承認対象者リストと日付リストから承認状況を取得する（月別）
		EmpPerformMonthParamImport param = new EmpPerformMonthParamImport(new YearMonth(processDateYM), closureId,
				new ClosureDate(closureDate.day(), closureDate.day() == closureDate.lastDateInMonth()), closureDate,
				employeeId);
		List<ApproveRootStatusForEmpImport> lstApprovalState = this.approvalStatusAdapter.getAppRootStatusByEmpsMonth(Arrays.asList(param));
		if(!CollectionUtil.isEmpty(lstApprovalState) && lstApprovalState.get(0).getApprovalStatus() == ApprovalStatusForEmployee.APPROVED){
			return ApprovalStatus.APPROVAL;
		}
		return ApprovalStatus.UNAPPROVAL;
	}
	/**
	 * 社員が対象月の承認処理を利用できるかチェックする
	 * @param cId: ログイン会社に一致する
	 * @param employeeId: 社員ID
	 * @param baseDate: 基準日：年月日
	 * @return
	 */
	public boolean canUseMonthlyApprovalCheck(String cId, String employeeId, GeneralDate baseDate,Optional<ApprovalProcess> approvalProcOp,List<SharedAffJobTitleHisImport> listShareAff){
		// 対応するドメインモデル「承認処理の利用設定」を取得する
		if (approvalProcOp.isPresent()) {
			// 「月の承認者確認を利用する」をチェックする
			if (approvalProcOp.get().getUseMonthBossChk() == 0)
				return false;
			// Imported「（就業）所属職位履歴」を取得する
			// 承認処理が必要な職位かチェックする
			// パラメータ「社員の職位ID」がドメインモデル「承認処理の利用設定．承認処理が必要な職位」に該当するかチェックする
			for (SharedAffJobTitleHisImport sharedAffJobTitleHisImport : listShareAff) {
				if (sharedAffJobTitleHisImport.getEmployeeId().equals(employeeId) && approvalProcOp.get().getJobTitleId().equals(sharedAffJobTitleHisImport.getJobTitleId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
