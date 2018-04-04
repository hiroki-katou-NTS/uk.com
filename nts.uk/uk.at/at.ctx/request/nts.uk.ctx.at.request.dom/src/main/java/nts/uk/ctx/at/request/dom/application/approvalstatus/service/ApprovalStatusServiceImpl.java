package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmploymentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PeriodOutput;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;

@Stateless
public class ApprovalStatusServiceImpl implements ApprovalStatusService {
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;

	@Override
	public List<ApprovalStatusEmployeeOutput> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {

		List<ApprovalStatusEmployeeOutput> listSttEmp = new ArrayList<ApprovalStatusEmployeeOutput>();
		// imported(申請承認)「社員ID（リスト）」を取得する
		PeriodOutput closurePeriod = new PeriodOutput(closureStart, closureEnd);
		List<String> listSId = employeeRequestAdapter.getListSIdByWkpIdAndPeriod(wkpId, closurePeriod.getStartDate(),
				closurePeriod.getEndDate());
		// Waiting for Q&A
		PeriodOutput entryLeavePeriod = new PeriodOutput(GeneralDate.fromString("2018/01/01", "yyyy/MM/dd"),
				GeneralDate.fromString("2018/05/02", "yyyy/MM/dd"));
		// 社員ID(リスト)
		for (String sId : listSId) {
			// imported(就業)「所属雇用履歴」より雇用コードを取得する
			List<EmploymentOutput> listEmpHist = new ArrayList<EmploymentOutput>();
			// Waiting for Q&A
			PeriodOutput empPeriod = new PeriodOutput(GeneralDate.fromString("2018/01/02", "yyyy/MM/dd"),
					GeneralDate.fromString("2018/02/02", "yyyy/MM/dd"));

			// 雇用（リスト）
			for (EmploymentOutput sttEmp : listEmpHist) {
				// 存在しない場合
				if (listEmpCd.contains(sttEmp.getEmpCd())) {
					continue;
				}
				// 存在する場合
				// アルゴリズム「承認状況対象期間取得」を実行する
				PeriodOutput sttPeriod = this.getApprovalSttPeriod(sId, empPeriod, closurePeriod, entryLeavePeriod);
				listSttEmp
						.add(new ApprovalStatusEmployeeOutput(sId, sttPeriod.getStartDate(), sttPeriod.getStartDate()));
			}
		}
		return listSttEmp;
	}

	/**
	 * アルゴリズム「承認状況対象期間取得」を実行する
	 * 
	 * @param sId
	 *            社員ID
	 * @param empPeriod
	 *            雇用期間（開始日、終了日）
	 * @param closurePeriod
	 *            締め期間（開始日、終了日)
	 * @param inOutPeriod
	 *            入退社期間（入社年月日、退社年月日
	 * @return
	 */
	private PeriodOutput getApprovalSttPeriod(String sId, PeriodOutput empPeriod, PeriodOutput closurePeriod,
			PeriodOutput inOutPeriod) {
		GeneralDate startDate;
		GeneralDate endDate;
		// 雇用期間（開始日）≦締め期間（開始日）
		if (empPeriod.getStartDate().beforeOrEquals(closurePeriod.getStartDate())) {
			// 対象期間.開始日＝締め期間（開始日）
			startDate = closurePeriod.getStartDate();
		} else {
			// 対象期間.開始日＝雇用期間（開始日）
			startDate = empPeriod.getStartDate();
		}
		// 対象期間.開始日≦入退社期間（入社年月日）
		if (startDate.beforeOrEquals(inOutPeriod.getStartDate())) {
			// 対象期間.開始日＝入退社期間（入社年月日）
			startDate = inOutPeriod.getStartDate();
		}
		// 雇用期間（終了日）≧締め期間（終了日）
		if (empPeriod.getEndDate().afterOrEquals(closurePeriod.getEndDate())) {
			// 対象期間終了日＝締め期間（終了日）
			endDate = closurePeriod.getEndDate();
		} else {
			// 対象期間.終了日＝雇用期間（終了日）
			endDate = empPeriod.getEndDate();
		}
		// 対象期間.開始日≧入退社期間（退社年月日）
		if (endDate.afterOrEquals(inOutPeriod.getEndDate())) {
			// 対象期間.開始日＝入退社期間（退社年月日）
			endDate = inOutPeriod.getEndDate();
		}
		return new PeriodOutput(startDate, endDate);
	}
}
