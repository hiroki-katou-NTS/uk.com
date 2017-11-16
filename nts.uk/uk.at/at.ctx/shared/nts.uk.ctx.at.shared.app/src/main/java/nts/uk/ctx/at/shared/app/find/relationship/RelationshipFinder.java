package nts.uk.ctx.at.shared.app.find.relationship;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class RelationshipFinder {
	@Inject
	private RelationshipRepository relaRep;
	/**
	 * get all relationship
	 * @return
	 */
	public List<RelationshipDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.relaRep.findAll(companyId).stream().map(item->{
			return new RelationshipDto(item.getRelationshipCode().v(), item.getRelationshipName().v());
		}).collect(Collectors.toList());
	}
}
