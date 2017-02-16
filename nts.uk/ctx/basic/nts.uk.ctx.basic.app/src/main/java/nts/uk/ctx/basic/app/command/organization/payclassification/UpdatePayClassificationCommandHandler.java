package nts.uk.ctx.basic.app.command.organization.payclassification;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassification;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationCode;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationName;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class UpdatePayClassificationCommandHandler extends CommandHandler<UpdatePayClassificationCommand> {

	@Inject
	private PayClassificationRepository payClassificationRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdatePayClassificationCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		if(!payClassificationRepository.isExisted(companyCode, 
				new PayClassificationCode(context.getCommand().getPayClassificationCode()))){
			//throw err[ER026]
		}
//		PayClassification payClassification = new PayClassification(new PayClassificationName(context.getCommand().getPayClassificationName()),
//				new PayClassificationCode(context.getCommand().getPayClassificationCode()),
//				companyCode,
//				new Memo(context.getCommand().getMemo()));
		//payClassificationRepository.update(payClassification);
	}

}
