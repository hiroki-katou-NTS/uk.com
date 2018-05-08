package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;

@Stateless
public class DeleteAgreeCondOtCommandHandler extends CommandHandler<DeleteAgreeCondOtCommand>{
	@Inject
	private IAgreeCondOtRepository otRep;
	@Override
	protected void handle(CommandHandlerContext<DeleteAgreeCondOtCommand> context) {
		List<ParamDelete> id = context.getCommand().getDeleteAgreeCondOt();
		for(ParamDelete obj: id){
			Optional<AgreeCondOt> domain = otRep.findById(obj.getId(), obj.getNo());
			if(!domain.isPresent()){
				throw new RuntimeException("対象データがありません。");
			}else{
				otRep.delete(obj.getId(), obj.getNo());;
			}
		}
	}
}
