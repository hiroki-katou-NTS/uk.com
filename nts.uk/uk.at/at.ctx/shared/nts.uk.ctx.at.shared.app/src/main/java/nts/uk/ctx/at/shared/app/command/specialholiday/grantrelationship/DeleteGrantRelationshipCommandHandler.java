package nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.repository.GrantRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * delete grant relationship command handler
 * @author yennth
 *
 */
@Stateless
public class DeleteGrantRelationshipCommandHandler extends CommandHandler<DeleteGrantRelationshipCommand>{
	@Inject
	private GrantRelationshipRepository grantRelaRep;

	@Override
	protected void handle(CommandHandlerContext<DeleteGrantRelationshipCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<GrantRelationship> grantRelationshipOld = grantRelaRep.findByCode(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getRelationshipCode());
		if(!grantRelationshipOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		grantRelaRep.delete(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getRelationshipCode());
	}
	
}
