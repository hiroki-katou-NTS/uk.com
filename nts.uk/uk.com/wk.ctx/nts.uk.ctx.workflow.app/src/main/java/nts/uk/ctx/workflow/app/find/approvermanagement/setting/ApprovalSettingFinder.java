package nts.uk.ctx.workflow.app.find.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;
/**
 * @author yennth
 */
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ApprovalSettingFinder {
	@Inject
	private ApprovalSettingRepository approvalRep;
	
	
	public ApprovalSettingDto findApproSet(){
		String companyId = AppContexts.user().companyId();
		Optional<PrincipalApprovalFlg> approSet = approvalRep.getPrincipalByCompanyId(companyId);
		if(approSet.isPresent()){
			return new ApprovalSettingDto(companyId, approSet.get().value);
		}
		return null;
	}
}
