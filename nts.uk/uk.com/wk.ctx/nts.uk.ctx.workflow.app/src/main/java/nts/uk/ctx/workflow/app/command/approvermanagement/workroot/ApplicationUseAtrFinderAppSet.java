package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;


import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
/**
 * change Setting kaf022 to workflow
 * @author hoangnd
 *
 */
@Stateless
public class ApplicationUseAtrFinderAppSet {
	@Inject 
	private ApprovalSettingRepository approvalSettingRepository;
	public ApproverRegisterSetDto getAppSet(String companyId){
		Optional<ApprovalSetting> approvalSetting = approvalSettingRepository.getApprovalByComId(companyId);
		if(approvalSetting.isPresent()) {
			ApproverRegisterSet approverRegsterSet = approvalSetting.get().getApproverRegsterSet();
			return ApproverRegisterSetDto.fromDomain(approverRegsterSet);
		}
		return new ApproverRegisterSetDto(1, 1, 1);
	}
}
