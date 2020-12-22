package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

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
	 * 申請未承認人数
	 */
	private int countUnApprApp;
	
	/**
	 * 日別未確認人数
	 */
	private int countUnConfirmDay;
	
	/**
	 * 日別未承認人数
	 */
	private int countUnApprDay;
	
	/**
	 * 月別未確認人数
	 */
	private int countUnConfirmMonth;
	
	/**
	 * 月別未承認人数
	 */
	private int countUnApprMonth;
	
	/**
	 * 確定表示
	 */
	private boolean displayConfirm;
	
	/**
	 * 確定者
	 */
	private String confirmPerson;
	
	/**
	 * 日付
	 */
	private GeneralDate date;
	
	public ApprSttExecutionOutput(DisplayWorkplace displayWorkplace) {
		this.wkpID = displayWorkplace.getId();
		this.wkpCD = displayWorkplace.getCode();
		this.wkpName = displayWorkplace.getName();
		this.hierarchyCode = displayWorkplace.getHierarchyCode();
		this.empPeriodLst = Collections.emptyList();
		this.countEmp = 0;
		this.countUnApprApp = 0;
		this.countUnConfirmDay = 0;
		this.countUnApprDay = 0;
		this.countUnConfirmMonth = 0;
		this.countUnApprMonth = 0;
		this.displayConfirm = false;
		this.confirmPerson = "";
		this.date = null;
	}
	
}
