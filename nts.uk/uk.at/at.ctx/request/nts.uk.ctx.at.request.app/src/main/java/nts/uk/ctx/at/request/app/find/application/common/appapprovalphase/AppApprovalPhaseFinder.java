package nts.uk.ctx.at.request.app.find.application.common.appapprovalphase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppApprovalPhaseFinder {
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	public Optional<AppApprovalPhaseDto> findByCode(String appID , String phaseID) {
		String companyID = AppContexts.user().companyId();
		return this.appApprovalPhaseRepository.findByCode(companyID, appID,phaseID  )
				.map(appApprovalPhase -> AppApprovalPhaseDto.fromDomain(appApprovalPhase));
	}
	public List<AppApprovalPhaseDto> findPhaseByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		return this.appApprovalPhaseRepository.findPhaseByAppID(companyID, appID)
				.stream().map(appApprovalPhase -> AppApprovalPhaseDto.fromDomain(appApprovalPhase)).collect(Collectors.toList());
	}
}

