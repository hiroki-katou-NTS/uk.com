package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;

@Value
public class ApprovalSettingParamCommand {

	/**
	 * 承認フェーズ
	 */
	private List<ApproverInformationCommand> approvalPhases;

	/**
	 * 承認ルート
	 */
	private ApprovalRootInformationCommand approvalRootInfo;

	public ApprovalSettingParam toDomain() {
		return new ApprovalSettingParam(
				this.approvalPhases.stream().map(ApproverInformationCommand::toDomain).collect(Collectors.toList()),
				this.approvalRootInfo.toDomain());
	}
}
