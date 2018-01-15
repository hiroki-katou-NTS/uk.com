package nts.uk.ctx.at.shared.app.command.relationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * delete relationship command handler
 * @author yennth
 *
 */
@Stateless
public class DeleteRelationshipCommandHandler extends CommandHandler<DeleteRelationshipCommand>{
	@Inject	
	private RelationshipRepository relaRep;
	@Override
	protected void handle (CommandHandlerContext<DeleteRelationshipCommand> context){
		String companyId = AppContexts.user().companyId();
		Optional<Relationship> relationshipOld = relaRep.findByCode(companyId, context.getCommand().getRelationshipCode());
		if(!relationshipOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		relaRep.delete(companyId, context.getCommand().getRelationshipCode());
	}
}
