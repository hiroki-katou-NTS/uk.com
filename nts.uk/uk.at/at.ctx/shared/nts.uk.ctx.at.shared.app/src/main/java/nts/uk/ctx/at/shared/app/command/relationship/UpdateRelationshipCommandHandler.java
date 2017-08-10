package nts.uk.ctx.at.shared.app.command.relationship;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

public class UpdateRelationshipCommandHandler extends CommandHandler<UpdateRelationshipCommand> {
	@Inject
	private RelationshipRepository relaRep;
	@Override
	protected void handle (CommandHandlerContext<UpdateRelationshipCommand> context){
		String companyId = AppContexts.user().companyId();
		Optional<Relationship> relationshipOld =  relaRep.getByCode(companyId, context.getCommand().getRelationshipCd());
		if(!relationshipOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		Relationship relationshipNew = Relationship.createFromJavaType(companyId, context.getCommand().getRelationshipCd(), context.getCommand().getRelationshipName());
		relaRep.updateRelationship(relationshipNew);
	}
}
