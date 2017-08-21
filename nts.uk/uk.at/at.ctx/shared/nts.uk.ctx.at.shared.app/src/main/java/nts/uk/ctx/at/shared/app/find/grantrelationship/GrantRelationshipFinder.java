package nts.uk.ctx.at.shared.app.find.grantrelationship;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.shared.dom.grantrelationship.repository.GrantRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */

@Stateless
public class GrantRelationshipFinder {
	@Inject
	private GrantRelationshipRepository grantRelaRep;
	public List<GrantRelationshipDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.grantRelaRep.findAll(companyId).stream().map(item -> {
			return new GrantRelationshipDto(item.getSpecialHolidayCode(), 
											item.getRelationshipCode(),
											item.getGrantRelationshipDay().v(),
											item.getMorningHour().v());
		}).collect(Collectors.toList());
	}
}
