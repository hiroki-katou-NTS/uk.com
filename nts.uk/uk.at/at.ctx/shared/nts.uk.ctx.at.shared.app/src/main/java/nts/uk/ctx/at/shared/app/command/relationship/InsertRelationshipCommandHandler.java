package nts.uk.ctx.at.shared.app.command.relationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * insert relationship Command handler
 * 
 * @author yennth
 *
 */
@Stateless
public class InsertRelationshipCommandHandler extends CommandHandler<InsertRelationshipCommand> {
	@Inject
	private RelationshipRepository relashipRep;

	@Override
	protected void handle(CommandHandlerContext<InsertRelationshipCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertRelationshipCommand cmd = context.getCommand();
		Relationship relationship = Relationship.createFromJavaType(companyId, cmd.getRelationshipCode(),
				cmd.getRelationshipName(), cmd.isThreeParentOrLess());
		Optional<Relationship> relationshipOld = relashipRep.findByCode(companyId,
				context.getCommand().getRelationshipCode());
		if (relationshipOld.isPresent()) {
			throw new BusinessException("Msg_3");
		}
		relashipRep.insert(relationship);
	}
}
