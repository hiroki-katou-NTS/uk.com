package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ApprovalPhaseDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;

@Data
@AllArgsConstructor
public class ApprovalSettingInformationDto {

	/** 承認フェーズ */
	private List<ApprovalPhaseDto> approvalPhases;

	/** 承認ルート */
	private PersonApprovalRootDto personApprovalRoot;

	public static ApprovalSettingInformationDto fromDomain(ApprovalSettingInformation domain) {
		return new ApprovalSettingInformationDto(
				domain.getApprovalPhases().stream().map(ApprovalPhaseDto::fromDomain).collect(Collectors.toList()),
				PersonApprovalRootDto.fromDomain(domain.getPersonApprovalRoot()));
	}
}
