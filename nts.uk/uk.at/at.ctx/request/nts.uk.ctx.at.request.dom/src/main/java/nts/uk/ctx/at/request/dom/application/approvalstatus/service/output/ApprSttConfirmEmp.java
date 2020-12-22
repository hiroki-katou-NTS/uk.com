package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttConfirmEmp {
	/**
	 * 社員コード
	 */
	private String empCD;
	
	/**
	 * 社員名
	 */
	private String empName;
	
	/**
	 * 社員ID
	 */
	private String empID;
	
	/**
	 * 月別本人確認
	 */
	private String sttUnConfirmDay;
	
	/**
	 * 月別上長承認
	 */
	private String sttUnApprDay;
	
	/**
	 * 日別本人確認
	 */
	private String sttUnConfirmMonth;
	
	/**
	 * 日別上長承認
	 */
	private String sttUnApprMonth;
	
	/**
	 * 期間（開始日～終了日）
	 */
	private List<ApprSttEmpDate> dateInfoLst;
}
