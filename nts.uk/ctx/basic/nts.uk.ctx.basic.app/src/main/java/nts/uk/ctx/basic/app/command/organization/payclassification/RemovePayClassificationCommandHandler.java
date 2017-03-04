package nts.uk.ctx.basic.app.command.organization.payclassification;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassification;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationCode;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemovePayClassificationCommandHandler extends CommandHandler<RemovePayClassificationCommand> {

	@Inject
	private PayClassificationRepository payClassificationRepository;

	@Override
	protected void handle(CommandHandlerContext<RemovePayClassificationCommand> context) {
//		RemovePayClassificationCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		
//		Optional<PayClassification> payclass = payClassificationRepository.findSinglePayClassification(companyCode, command.getPayClassificationCode());
//		
//		if (payclass.isPresent()) {
//			throw new BusinessException("Not found");
//		}
//		
//		
//		if(!payClassificationRepository.isExisted(companyCode, 
//				new PayClassificationCode(context.getCommand().getPayClassificationCode()))){
//			//throw err[ER010]
//			throw new BusinessException(new RawErrorMessage("ER010"));
//		}
		payClassificationRepository.remove(companyCode,
				new PayClassificationCode(context.getCommand().getPayClassificationCode()));

	}

}
