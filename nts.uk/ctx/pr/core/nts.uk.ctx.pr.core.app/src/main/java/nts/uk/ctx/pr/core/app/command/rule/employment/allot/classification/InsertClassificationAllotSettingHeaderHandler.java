package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeader;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertClassificationAllotSettingHeaderHandler extends CommandHandler<InsertClassificationAllotSettingHeaderCommand> {
	@Inject
	private ClassificationAllotSettingHeaderRepository classRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertClassificationAllotSettingHeaderCommand> context) {
		InsertClassificationAllotSettingHeaderCommand command = context.getCommand();
		String compayCode = AppContexts.user().companyCode();
		ClassificationAllotSettingHeader domain = ClassificationAllotSettingHeader.createFromJavaType(compayCode, command.getHistoryId(),command.getStartDateYM(), command.getEndDateYM());
		classRepository.insert(domain);
	}
	
}
