package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttExecutionOutput;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttExecutionDto {
	private String wkpID;
	
	private String wkpCD;
	
	/**
	 * 職場名
	 */
	private String wkpName;
	
	private String hierarchyCode;
	
	private List<EmpPeriodDto> empPeriodLst;
	
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
	private String date;
	
	public static ApprSttExecutionDto fromDomain(ApprSttExecutionOutput apprSttExecutionOutput) {
		return new ApprSttExecutionDto(
				apprSttExecutionOutput.getWkpID(), 
				apprSttExecutionOutput.getWkpCD(), 
				apprSttExecutionOutput.getWkpName(), 
				apprSttExecutionOutput.getHierarchyCode(), 
				apprSttExecutionOutput.getEmpPeriodLst().stream().map(x -> EmpPeriodDto.fromDomain(x)).collect(Collectors.toList()),
				apprSttExecutionOutput.getCountEmp(), 
				apprSttExecutionOutput.getCountUnApprApp(),
				apprSttExecutionOutput.getCountUnConfirmDay(),
				apprSttExecutionOutput.getCountUnApprDay(),
				apprSttExecutionOutput.getCountUnConfirmMonth(),
				apprSttExecutionOutput.getCountUnApprMonth(),
				apprSttExecutionOutput.isDisplayConfirm(),
				apprSttExecutionOutput.getConfirmPerson(),
				apprSttExecutionOutput.getDate() == null ? null : apprSttExecutionOutput.getDate().toString());
	}
}
