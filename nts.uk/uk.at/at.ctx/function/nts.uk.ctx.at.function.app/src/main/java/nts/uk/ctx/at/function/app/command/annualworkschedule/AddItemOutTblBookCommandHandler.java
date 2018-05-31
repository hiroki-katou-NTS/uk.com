package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.ItemOutTblBookRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddItemOutTblBookCommandHandler extends CommandHandler<ItemOutTblBookCommand> {

	@Inject
	private ItemOutTblBookRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ItemOutTblBookCommand> context) {
		String companyId = AppContexts.user().companyId();
		ItemOutTblBookCommand addCommand = context.getCommand();
		repository.add(ItemOutTblBook.createFromJavaType(companyId,
					addCommand.getSetOutCd(), addCommand.getCd(), addCommand.getSortBy(),
					addCommand.getHeadingName(), addCommand.isUseClass(),
					addCommand.getValOutFormat(),
					addCommand.getListOperationSetting().stream().map(m ->
						CalcFormulaItem.createFromJavaType(companyId, m.getSetOutCd(),
							m.getItemOutCd(), m.getAttendanceItemId(), m.getOperation())
					).collect(Collectors.toList())));
	}
}
