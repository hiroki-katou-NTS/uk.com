package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */

/**
 * 10-2.詳細画面解除後の処理
 */
@Stateless
public class AfterDetailScreenProcessFinder {

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	public Optional<AfterDetailScreenProcessDto> findByCode(){
		String companyID = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();
		String phaseID = IdentifierUtil.randomUniqueId();
		
		return this.appApprovalPhaseRepository
				.findByCode(companyID, appID, phaseID)
				.map(appApprovalPhase -> AfterDetailScreenProcessDto.fromDomain(appApprovalPhase) );
	}
}

