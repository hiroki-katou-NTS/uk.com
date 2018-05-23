package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import java.util.List;

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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;

@Stateless
@Transactional
public class UpdateAgreeConditionErrorCommandHandler extends CommandHandler<UpdateAgreeConditionErrorCommand> {
	@Inject
	private IAgreeConditionErrorRepository conErrRep;

	@Inject
	private IAgreeCondOtRepository otRep;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateAgreeConditionErrorCommand> context) {
//		List<AgreeConditionError> listDomain = new ArrayList<>();
//		listDomain = context.getCommand().getAgreeConditionErrorCommand().stream().map(x -> 
//			AgreeConditionError.createFromJavaType(x.getId(), x.getCompanyId(), x.getCategory(),
//													x.getCode(), x.getUseAtr(), x.getPeriod(), 
//													x.getErrorAlarm(), x.getMessageDisp())
//		).collect(Collectors.toList());
//		for(AgreeConditionError item : listDomain){
//			if(item.getId() != null){
//				Optional<AgreeConditionError> oldOption = conErrRep.findById(item.getId(), item.getCode().v(), 
//																				item.getCompanyId(), item.getCategory().value);
//				if(oldOption.isPresent()){
//					conErrRep.update(item);
//				}else{
//					conErrRep.insert(item);
//				}
//			}else{
//				conErrRep.insert(item);
//			}
//		}
		List<DeleteAgreeConditionErrorCommand> listErrorDel = context.getCommand().getDeleteCondError();  
		for(DeleteAgreeConditionErrorCommand obj : listErrorDel){
			this.conErrRep.delete(obj.getCode(), obj.getCategory());
		}
		
		
		List<DeleteAgreeCondOtCommand> listOtDel = context.getCommand().getDeleteCondOt();
		for(DeleteAgreeCondOtCommand item : listOtDel){
			this.otRep.delete(item.getCode(), item.getCategory());
		}
	}

}
