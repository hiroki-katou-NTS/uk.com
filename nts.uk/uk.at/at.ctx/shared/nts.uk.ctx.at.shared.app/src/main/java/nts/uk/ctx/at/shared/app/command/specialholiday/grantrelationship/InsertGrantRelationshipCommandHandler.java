package nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.repository.GrantRelationshipRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
/**
 * Insert/update Grant Relationship
 * @author yennth
 *
 */
public class InsertGrantRelationshipCommandHandler extends CommandHandler<InsertGrantRelationshipCommand>{
	@Inject
	private GrantRelationshipRepository grantRelaRep;

	@Override
	protected void handle(CommandHandlerContext<InsertGrantRelationshipCommand> context) {
		String companyId = AppContexts.user().companyId();
		if(context.getCommand().getMorningHour()!=null && context.getCommand().getMorningHour()>10){
				throw new BusinessException("Msg_372");
		}
		GrantRelationship grantRelationship = GrantRelationship.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(),	
																					context.getCommand().getRelationshipCode(),
																					context.getCommand().getGrantRelationshipDay(),
									 												context.getCommand().getMorningHour());
		grantRelationship.validate();
		// find a item in database
		Optional<GrantRelationship> grantRelationshipOld = grantRelaRep.findByCode(companyId, context.getCommand().getSpecialHolidayCode(),
																					context.getCommand().getRelationshipCode());
		// if a item exist in database, update. If don't exist, insert
		if(!grantRelationshipOld.isPresent()){
			grantRelaRep.insert(grantRelationship);
		}
		else{
			grantRelaRep.update(grantRelationship);
		}
	}
	
}
