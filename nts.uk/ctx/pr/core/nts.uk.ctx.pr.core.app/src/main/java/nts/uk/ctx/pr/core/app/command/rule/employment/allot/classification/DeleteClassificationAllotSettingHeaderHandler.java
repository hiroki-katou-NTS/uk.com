package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeader;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

public class DeleteClassificationAllotSettingHeaderHandler extends CommandHandler<DeleteClassificationAllotSettingHeaderCommand>{

	@Inject
	private ClassificationAllotSettingHeaderRepository classRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteClassificationAllotSettingHeaderCommand> context) {
		DeleteClassificationAllotSettingHeaderCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		ClassificationAllotSettingHeader domain = ClassificationAllotSettingHeader.createFromJavaType(companyCode,
				command.getHistoryId(), command.getStartDateYM(), command.getEndDateYM());
		classRepository.delete(domain);
	}
	
	
}
