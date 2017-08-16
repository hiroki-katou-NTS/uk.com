package nts.uk.ctx.at.shared.app.command.relationship;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

public class InsertRelationshipCommandHandler extends CommandHandler<InsertRelationshipCommand>{
	@Inject
	private RelationshipRepository relashipRep;
	@Override
	protected void handle(CommandHandlerContext<InsertRelationshipCommand> context){
		String companyId = AppContexts.user().companyId();
		Relationship relationship = Relationship.createFromJavaType(companyId, context.getCommand().getRelationshipCd(),
																	context.getCommand().getRelationshipName());
		Optional<Relationship> relationshipOld = relashipRep.getByCode(companyId, context.getCommand().getRelationshipCd());
		if(relationshipOld.isPresent()){
			throw new BusinessException("Msg_3");
		}
		relashipRep.insertRelationship(relationship);
	}
}
