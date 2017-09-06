package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyFinder {
	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;

	public GoBackDirectlyDto getGoBackDirectlyByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		return goBackDirectRepo.findByApplicationID(companyID, appID).map(c -> convertToDto(c)).orElse(null);

	}

	// Convert to Dto
	public GoBackDirectlyDto convertToDto(GoBackDirectly domain) {
		return new GoBackDirectlyDto(domain.getCompanyID(), domain.getAppID(), domain.getWorkTypeCD().v(),
				domain.getSiftCd().v(), domain.getWorkChangeAtr().value, domain.getGoWorkAtr1().value,
				domain.getBackHomeAtr1().value, domain.getWorkTimeStart1().v(), domain.getWorkTimeEnd1().v(),
				domain.getWorkLocationCD1(), domain.getGoWorkAtr2().value, domain.getBackHomeAtr2().value,
				domain.getWorkTimeStart2().v(), domain.getWorkTimeEnd2().v(), domain.getWorkLocationCD2());
	}

}
