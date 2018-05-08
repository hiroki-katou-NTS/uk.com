package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;

@Stateless
public class DeleteAgreeConditionErrorCommandHandler  extends CommandHandler<DeleteAgreeConditionErrorCommand>{
	@Inject
	private IAgreeConditionErrorRepository conErrorRep;

	@Override
	protected void handle(CommandHandlerContext<DeleteAgreeConditionErrorCommand> context) {
		List<String> id = context.getCommand().getId();
		for(String obj: id){
			Optional<AgreeConditionError> domain = conErrorRep.findById(obj);
			if(!domain.isPresent()){
				throw new RuntimeException("対象データがありません。");
			}else{
				conErrorRep.delete(obj);
			}
		}
	}
}
