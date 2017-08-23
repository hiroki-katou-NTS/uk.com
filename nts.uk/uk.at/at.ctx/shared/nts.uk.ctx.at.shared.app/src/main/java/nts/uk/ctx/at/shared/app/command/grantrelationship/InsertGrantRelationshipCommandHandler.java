package nts.uk.ctx.at.shared.app.command.grantrelationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.grantrelationship.repository.GrantRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class InsertGrantRelationshipCommandHandler extends CommandHandler<InsertGrantRelationshipCommand>{
	@Inject
	private GrantRelationshipRepository grantRelaRep;

	@Override
	protected void handle(CommandHandlerContext<InsertGrantRelationshipCommand> context) {
		String companyId = AppContexts.user().companyId();
		if(context.getCommand().getMorningHour()!=null){
			if(context.getCommand().getMorningHour()>10){
				throw new BusinessException("Msg_372");
			}
		}
		GrantRelationship grantRelationship = GrantRelationship.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(),	
																					context.getCommand().getRelationshipCode(),
																					context.getCommand().getGrantRelationshipDay(),
									 												context.getCommand().getMorningHour());
		Optional<GrantRelationship> grantRelationshipOld = grantRelaRep.findByCode(companyId, context.getCommand().getSpecialHolidayCode(),
																					context.getCommand().getRelationshipCode());
		if(!grantRelationshipOld.isPresent()){
			grantRelaRep.insert(grantRelationship);
		}
		else{
			grantRelaRep.update(grantRelationship);
		}
	}
	
}
