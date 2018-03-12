package nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;
/**
 * 
 * @author yennth
 *
 */
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSetRepository;
@Stateless
public class ApprovalSetFinder {
	@Inject
	private ApprovalSetRepository approRep;
	public ApprovalSetDto findByComId(){
		Optional<ApprovalSet> appSet = approRep.getApproval();
		if(appSet.isPresent()){
			return ApprovalSetDto.convertToDto(appSet.get());
		}
		return null;
	}
}
