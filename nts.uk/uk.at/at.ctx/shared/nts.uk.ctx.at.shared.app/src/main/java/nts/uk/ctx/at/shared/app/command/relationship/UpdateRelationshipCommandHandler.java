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
 * update relationship command handler
 * 
 * @author yennth
 *
 */
@Stateless
public class UpdateRelationshipCommandHandler extends CommandHandler<UpdateRelationshipCommand> {
	@Inject
	private RelationshipRepository relaRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateRelationshipCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<Relationship> relationshipOld = relaRep.findByCode(companyId,
				context.getCommand().getRelationshipCode());

		UpdateRelationshipCommand cmd = context.getCommand();
		if (!relationshipOld.isPresent()) {
			throw new RuntimeException("対象データがありません。");
		}
		Relationship relationshipNew = Relationship.createFromJavaType(companyId, cmd.getRelationshipCode(),
				cmd.getRelationshipName(), cmd.isThreeParentOrLess());
		relaRep.update(relationshipNew);
	}
}
