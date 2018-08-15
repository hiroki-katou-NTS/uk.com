package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.grantdayperrelationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GrantDayRelationshipFinder {
	@Inject
	private GrantDayRelationshipRepository gDRelpRepo;

	public GrantDayRelationshipDto findByKey(int sHENo, String relpCd) {
		String companyId = AppContexts.user().companyId();
		Optional<GrantDayRelationship> dtoOpt = this.gDRelpRepo.findByCd(companyId, sHENo, relpCd);

		if (dtoOpt.isPresent()) {
			return GrantDayRelationshipDto.fromDomain(dtoOpt.get());

		}

		return null;

	}

}
