package nts.uk.ctx.at.request.app.find.application.approvalstatus;

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
	
	public static ApprSttSendMailInfoDto fromDomain(ApprSttSendMailInfoOutput apprSttSendMailInfoOutput, int mailType) {
		return new ApprSttSendMailInfoDto(
				ApprovalStatusMailTempDto.fromDomain(apprSttSendMailInfoOutput.getApprovalStatusMailTemp(), mailType)
				);
	}
}
