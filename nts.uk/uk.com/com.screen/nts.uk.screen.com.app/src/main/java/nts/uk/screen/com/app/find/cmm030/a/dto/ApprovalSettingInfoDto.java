package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApprovalPhaseDto;

@Data
@AllArgsConstructor
public class ApprovalSettingInfoDto {

	/** 承認フェーズ */
	private List<ApprovalPhaseDto> approvalPhases;
	
	/** 承認ルート */
	private PersonApprovalRootDto personApprovalRoot;
}
