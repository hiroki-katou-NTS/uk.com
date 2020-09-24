//package nts.uk.ctx.at.request.app.command.applicationreason;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//
//import nts.arc.error.BusinessException;
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
//import nts.uk.shr.com.context.AppContexts;
///**
// * 
// * @author yennth
// *
// */
//@Stateless
//@Transactional
//public class UpdateApplicationReasonCommandHandler extends CommandHandler<UpdateApplicationReasonCommand>{
//	@Inject
//	private ApplicationReasonRepository reasonRep;
//
//	@Override
//	protected void handle(CommandHandlerContext<UpdateApplicationReasonCommand> context) {
//		String companyId = AppContexts.user().companyId();
//		long count = 0;
//		List<Integer> listDefault = new ArrayList<>();
//		List<ApplicationReasonCommand> data = context.getCommand().getListCommand();
//		listDefault = data.stream().map(x -> x.getDefaultFlg()).collect(Collectors.toList());
//		for(int i = 0; i<listDefault.size(); i++){
//			if(!listDefault.isEmpty() && listDefault.get(i) == 1){
//				count = count +1;
//			}
//		}
//		if(count > 1){
//			throw new BusinessException("Can't update"); 
//		}
//		reasonRep.updateReason(data.stream().map(x -> ApplicationReason.createSimpleFromJavaType(companyId, x.getAppType(), 
//												x.getReasonID(), x.getDispOrder(), 
//												x.getReasonTemp(), x.getDefaultFlg()))
//											.collect(Collectors.toList()));
//	}
//	
//}
