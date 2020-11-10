package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttSendMailInfoOutput;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class ApprSttSendMailInfoDto {
	private ApprovalStatusMailTempDto approvalStatusMailTempDto;
	
	private List<ApprSttExecutionDto> apprSttExecutionDtoLst;
	
	public static ApprSttSendMailInfoDto fromDomain(ApprSttSendMailInfoOutput apprSttSendMailInfoOutput, int mailType) {
		return new ApprSttSendMailInfoDto(
				ApprovalStatusMailTempDto.fromDomain(apprSttSendMailInfoOutput.getApprovalStatusMailTemp(), mailType),
				apprSttSendMailInfoOutput.getApprSttExecutionOutputLst().stream().map(x -> ApprSttExecutionDto.fromDomain(x)).collect(Collectors.toList()));
	}
}
