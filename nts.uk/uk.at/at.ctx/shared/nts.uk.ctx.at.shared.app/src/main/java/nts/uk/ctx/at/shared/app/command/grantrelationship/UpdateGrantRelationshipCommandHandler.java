package nts.uk.ctx.at.shared.app.command.grantrelationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.grantrelationship.repository.GrantRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateGrantRelationshipCommandHandler extends CommandHandler<UpdateGrantRelationshipCommand>{
	@Inject
	private GrantRelationshipRepository grantRelaRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateGrantRelationshipCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<GrantRelationship> grantRelationshipOld = grantRelaRep.findByCode(companyId, context.getCommand().getSpecialHolidayCode(),
																					context.getCommand().getRelationshipCode());
		GrantRelationship grantRelationshipNew = GrantRelationship.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(),
																					context.getCommand().getRelationshipCode(),
																					context.getCommand().getGrantRelationshipDay(),
																					context.getCommand().getMorningHour());
		if(grantRelationshipOld.isPresent()){
			grantRelaRep.update(grantRelationshipNew);
		}
	}
	
}
