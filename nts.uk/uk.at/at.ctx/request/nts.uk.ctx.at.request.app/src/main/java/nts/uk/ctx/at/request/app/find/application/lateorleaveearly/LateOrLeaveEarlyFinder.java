package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class LateOrLeaveEarlyFinder {
	@Inject
	private LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;

	//TODO: anamite
	public LateOrLeaveEarlyDto getLateOrLeaveEarly() {
		String companyID = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();

		Optional<LateOrLeaveEarly> lateOrLeaveEarly = this.lateOrLeaveEarlyRepository.findByCode(companyID, appID);
		if (!lateOrLeaveEarly.isPresent()) {
			return null;
		}
		
		LateOrLeaveEarly result = lateOrLeaveEarly.get();
		
		AppReason appReason = lateOrLeaveEarlyRepository.findAppReason(companyID, result.getApplicationType());
		
		
//		return result.map(lateOrLeaveEarly -> LateOrLeaveEarlyDto.fromDomain(lateOrLeaveEarly, appReason));
		return null;
	}

	
}
