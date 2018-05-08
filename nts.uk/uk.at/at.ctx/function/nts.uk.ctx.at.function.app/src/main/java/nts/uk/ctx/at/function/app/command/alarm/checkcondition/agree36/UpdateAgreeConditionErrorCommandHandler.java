package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 * insert/update AgreeConditionError
 * @author yennth
 *
 */
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
@Stateless
@Transactional
public class UpdateAgreeConditionErrorCommandHandler extends CommandHandler<UpdateAgreeConditionErrorCommand>{
	@Inject
	private IAgreeConditionErrorRepository conErrRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateAgreeConditionErrorCommand> context) {
		List<AgreeConditionError> listDomain = new ArrayList<>();
		listDomain = context.getCommand().getAgreeConditionErrorCommand().stream().map(x -> {
			return new AgreeConditionError(x.getId(), x.getUseAtr(), x.getPeriod(), 
													x.getErrorAlarm(), x.getMessageDisp());
		}).collect(Collectors.toList());
		for(AgreeConditionError item : listDomain){
			if(item.getId() != null){
				Optional<AgreeConditionError> oldOption = conErrRep.findById(item.getId());
				if(oldOption.isPresent()){
					conErrRep.update(item);
				}else{
					conErrRep.insert(item);
				}
			}else{
				conErrRep.insert(item);
			}
		}
	}
	
}
