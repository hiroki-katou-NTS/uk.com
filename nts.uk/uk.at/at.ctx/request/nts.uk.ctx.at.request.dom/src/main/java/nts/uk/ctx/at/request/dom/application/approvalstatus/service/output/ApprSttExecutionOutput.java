package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class ApprSttExecutionOutput {
	
	private String wkpID;
	
	private String wkpCD;
	
	/**
	 * 職場名
	 */
	private String wkpName;
	
	private String hierarchyCode;
	
	private List<EmpPeriod> empPeriodLst;
	
	/**
	 * 対象人数
	 */
	private int countEmp;
	
	/**
	 * 申請未承認
	 */
	private int countUnApprApp;
	
	public ApprSttExecutionOutput(DisplayWorkplace displayWorkplace) {
		this.wkpID = displayWorkplace.getId();
		this.wkpCD = displayWorkplace.getCode();
		this.wkpName = displayWorkplace.getName();
		this.hierarchyCode = displayWorkplace.getHierarchyCode();
		this.empPeriodLst = Collections.emptyList();
		this.countEmp = 0;
		this.countUnApprApp = 0;
	}
	
}
