package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSetting;
import nts.uk.shr.com.context.AppContexts;

public class DeleteClassificationAllotSettingCommandHandler extends CommandHandler<DeleteClassificationAllotSettingCommand> {

	@Override
	protected void handle(CommandHandlerContext<DeleteClassificationAllotSettingCommand> context) {
		DeleteClassificationAllotSettingCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		ClassificationAllotSetting domain = ClassificationAllotSetting.createFromJavaType(companyCode, command.getHistoryId(), command.getClassificationCode(), command.getBonusDetailCode(), command.getPaymentDetailCode());
		
	}

	
}
