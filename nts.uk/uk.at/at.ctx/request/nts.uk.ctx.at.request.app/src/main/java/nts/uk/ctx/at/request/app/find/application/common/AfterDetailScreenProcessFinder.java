package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.AppApprovalPhaseRepository;

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
	
	public Optional<AfterDetailScreenProcessDto> findByCode(String companyID , String appID , String phaseID){
		return this.appApprovalPhaseRepository
				.findByCode(companyID, appID, phaseID)
				.map(appApprovalPhase -> AfterDetailScreenProcessDto.fromDomain(appApprovalPhase) );
	}
}

