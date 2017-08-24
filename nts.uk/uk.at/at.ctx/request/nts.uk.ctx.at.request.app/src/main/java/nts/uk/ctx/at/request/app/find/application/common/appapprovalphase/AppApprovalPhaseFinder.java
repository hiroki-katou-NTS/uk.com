package nts.uk.ctx.at.request.app.find.application.common.appapprovalphase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppApprovalPhaseFinder {
	
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	public Optional<AppApprovalPhaseDto> findByCode() {
		String companyID = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();
		String phaseID = IdentifierUtil.randomUniqueId();
		return this.appApprovalPhaseRepository.findByCode(companyID, appID,phaseID  )
				.map(appApprovalPhase -> AppApprovalPhaseDto.fromDomain(appApprovalPhase));
	}
	public List<AppApprovalPhaseDto> findPhaseByAppID() {
		String companyID = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();
		return this.appApprovalPhaseRepository.findPhaseByAppID(companyID, appID)
				.stream().map(appApprovalPhase -> AppApprovalPhaseDto.fromDomain(appApprovalPhase)).collect(Collectors.toList());
	}
}
