package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateClassificationAllotSettingCommandHandler extends CommandHandler<UpdateClassificationAllotSettingCommand>{

	@Override
	protected void handle(CommandHandlerContext<UpdateClassificationAllotSettingCommand> context) {
		UpdateClassificationAllotSettingCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		ClassificationAllotSetting domain = ClassificationAllotSetting.createFromJavaType(companyCode, command.getHistoryId(), command.getClassificationCode(), command.getBonusDetailCode(), command.getPaymentDetailCode());
		
	}
	
	
}
