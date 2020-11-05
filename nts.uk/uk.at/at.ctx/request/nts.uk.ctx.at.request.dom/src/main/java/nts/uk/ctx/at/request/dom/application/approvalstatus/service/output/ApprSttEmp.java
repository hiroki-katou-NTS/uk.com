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
public class ApprSttEmp {
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
	 * 期間（開始日～終了日）
	 */
	private List<ApprSttEmpDate> dateInfoLst;
}
