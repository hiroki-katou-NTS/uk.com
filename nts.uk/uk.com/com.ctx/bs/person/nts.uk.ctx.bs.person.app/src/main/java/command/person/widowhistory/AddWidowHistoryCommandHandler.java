package command.person.widowhistory;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistory;
import nts.uk.ctx.bs.person.dom.person.info.widowhistory.WidowHistoryRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
@Stateless
public class AddWidowHistoryCommandHandler extends CommandHandler<AddWidowHistoryCommand>
 implements PeregAddCommandHandler<AddWidowHistoryCommand>{
	
	@Inject
	private WidowHistoryRepository widowHistoryRepository;
	
	@Override
	public String targetCategoryId() {
		return "CS00014";
	}

	@Override
	public Class<?> commandClass() {
		return AddWidowHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<AddWidowHistoryCommand> context) {
		val command = context.getCommand();
		
		// Create new Id
		String newId = IdentifierUtil.randomUniqueId();
		
		WidowHistory widowHistory = WidowHistory.createObjectFromJavaType(newId, command.getWidowType(), command.getStartDate(), command.getEndDate());
		
		// Add WidowHistory
		widowHistoryRepository.addWidowHistory(widowHistory);
	}

}
