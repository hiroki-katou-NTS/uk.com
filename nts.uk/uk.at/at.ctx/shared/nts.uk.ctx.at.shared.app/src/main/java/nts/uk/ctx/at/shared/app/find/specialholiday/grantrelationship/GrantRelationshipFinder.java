package nts.uk.ctx.at.shared.app.find.specialholiday.grantrelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.repository.GrantRelationshipRepository;
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
	@Inject
	private RelationshipRepository relationshopRepo;
	
	public List<GrantRelationshipDto> finder(String sphdCode) {
		String companyId = AppContexts.user().companyId();
		
		List<Relationship> relationshipList = relationshopRepo.findAll(companyId);
		List<GrantRelationship> grantRelationshipList = this.grantRelaRep.findBySPCode(companyId, sphdCode);
		
		Map<String, GrantRelationship> grantRelationshipMap = grantRelationshipList.stream().collect(Collectors.toMap(GrantRelationship::getRelationshipCode, x->x));
		
		return relationshipList.stream().map(item -> {
			GrantRelationship grantRelationship = grantRelationshipMap.get(item.getRelationshipCode().v());
			
			return new GrantRelationshipDto(
					grantRelationship != null ? grantRelationship.getSpecialHolidayCode() : "00", 
					item.getRelationshipCode().v(),
					item.getRelationshipName().v(),
					grantRelationship != null && grantRelationship.getGrantRelationshipDay() != null ? grantRelationship.getGrantRelationshipDay().v() : null,
					grantRelationship != null && grantRelationship.getMorningHour() != null ? grantRelationship.getMorningHour().v() : null, 
					grantRelationship != null);
		}).collect(Collectors.toList());
	}
}
