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
	 * 申請未承認
	 */
	private int countUnApprApp;
	
	public static ApprSttExecutionDto fromDomain(ApprSttExecutionOutput apprSttExecutionOutput) {
		return new ApprSttExecutionDto(
				apprSttExecutionOutput.getWkpID(), 
				apprSttExecutionOutput.getWkpCD(), 
				apprSttExecutionOutput.getWkpName(), 
				apprSttExecutionOutput.getHierarchyCode(), 
				apprSttExecutionOutput.getEmpPeriodLst().stream().map(x -> EmpPeriodDto.fromDomain(x)).collect(Collectors.toList()),
				apprSttExecutionOutput.getCountEmp(), 
				apprSttExecutionOutput.getCountUnApprApp());
	}
}
