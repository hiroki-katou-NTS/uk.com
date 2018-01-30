package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTemp;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTempRepository;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class ApprovalTempFinder {
	@Inject
	private ApprovalTempRepository appRep;
	public ApprovalTempDto findByComId(){
		Optional<ApprovalTemp> appTemp = appRep.getAppTem();
		if(appTemp.isPresent()){
			return ApprovalTempDto.convertToDto(appTemp.get());
		}
		return null;
	}
}
