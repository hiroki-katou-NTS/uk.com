package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
@Value
@AllArgsConstructor
public class ApprovalFrameForAppDto {
	
	private Integer frameOrder;
	
	private List<ApproverStateForAppDto> listApprover;
	
	private int confirmAtr;
	
	private String appDate;
	
	public static ApprovalFrameForAppDto fromApprovalFrameImport(ApprovalFrameImport_New approvalFrameImport){
		return new ApprovalFrameForAppDto(
				approvalFrameImport.getFrameOrder(), 
				approvalFrameImport.getListApprover().stream().map(x -> ApproverStateForAppDto.fromDomain(x)).collect(Collectors.toList()),
				approvalFrameImport.getConfirmAtr(),
				approvalFrameImport.getAppDate().toString("yyyy/MM/dd"));
	}
	
	public ApprovalFrameImport_New toDomain() {
		return new ApprovalFrameImport_New(
				frameOrder, 
				listApprover.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				confirmAtr, 
				GeneralDate.fromString(appDate, "yyyy/MM/dd"));
	}
}
