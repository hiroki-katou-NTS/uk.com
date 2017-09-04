package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.gul.text.IdentifierUtil;
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

	public Optional<LateOrLeaveEarlyDto> getLateOrLeaveEarly() {
		String companyID = AppContexts.user().companyId();
		String appID = IdentifierUtil.randomUniqueId();

		return this.lateOrLeaveEarlyRepository.findByCode(companyID, appID)
				.map(lateOrLeaveEarly -> LateOrLeaveEarlyDto.fromDomain(lateOrLeaveEarly));
	}

	
}
