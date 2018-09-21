package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.StatementItemAndStatementItemNameDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemName;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateStatementItemNameCommandHandler extends CommandHandler<List<StatementItemAndStatementItemNameDto>> {

	@Inject
	private StatementItemNameRepository statementItemNameRepository;

	@Override
	protected void handle(CommandHandlerContext<List<StatementItemAndStatementItemNameDto>> context) {
		List<StatementItemAndStatementItemNameDto> command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<StatementItemName> statementItemName = command.stream()
				.map(item -> new StatementItemName(companyId, item.getSalaryItemId(), item.getName(),
						item.getShortName(), item.getOtherLanguageName(), item.getEnglishName()))
				.collect(Collectors.toList());
		statementItemNameRepository.updateListStatementItemName(statementItemName);
	}

};