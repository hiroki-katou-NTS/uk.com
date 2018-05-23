package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;

@Value
@AllArgsConstructor
public class EmpPerformanceOutput {
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 社員名
	 */
	private String sName;
	/**
	 * 社員ID.期間
	 */
	private GeneralDate startDate;
	/**
	 * 社員ID.期間
	 */
	private GeneralDate endDate;
	/**
	 * 承認ルートの状況
	 */
	private ApproveRootStatusForEmpImport routeStatus;
	/**
	 * 日別確認（リスト）＜職場ID、社員ID、対象日、本人確認、上司確認＞
	 */
	private List<DailyConfirmOutput> listDailyConfirm;
	/**
	 * エラー状況(リスト)＜職場ID、社員ID、対象日＞
	 */
	private List<ErrorStatusOutput> listErrorStatus;
}
