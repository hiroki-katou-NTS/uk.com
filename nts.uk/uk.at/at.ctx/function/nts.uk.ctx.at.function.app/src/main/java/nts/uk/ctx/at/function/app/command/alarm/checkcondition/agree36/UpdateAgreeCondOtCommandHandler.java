package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * Update AgreeCondOt
 * @author yennth
 *
 */
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
@Stateless
@Transactional
public class UpdateAgreeCondOtCommandHandler extends CommandHandler<UpdateAgreeCondOtCommand>{
	@Inject
	private IAgreeCondOtRepository otRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateAgreeCondOtCommand> context) {
		List<AgreeCondOt> listDomain = new ArrayList<>();
		listDomain = context.getCommand().getAgreeCondOtCommand().stream().map(x -> {
			return new AgreeCondOt(UUID.randomUUID().toString(), x.getNo(), x.getOt36(), x.getExcessNum(), x.getMessageDisp());
		}).collect(Collectors.toList());
		for(AgreeCondOt item : listDomain){
			Optional<AgreeCondOt> oldOption = otRep.findById(item.getId(), item.getNo());
			if(oldOption.isPresent()){
				otRep.update(item);
			}else{
				otRep.insert(item);
			}
		}
	}
}
