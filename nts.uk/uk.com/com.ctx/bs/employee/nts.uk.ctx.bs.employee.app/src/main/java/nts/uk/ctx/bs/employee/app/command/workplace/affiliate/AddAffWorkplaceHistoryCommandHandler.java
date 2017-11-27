package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory_ver1;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddAffWorkplaceHistoryCommandHandler extends CommandHandlerWithResult<AddAffWorkplaceHistoryCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>{
	
	@Inject
	private AffWorkplaceHistoryRepository_v1 affWorkplaceHistoryRepository;
	
	@Inject
	private AffWorkplaceHistoryItemRepository_v1 affWorkplaceHistoryItemRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00010";
	}

	@Override
	public Class<?> commandClass() {
		return AddAffWorkplaceHistoryCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddAffWorkplaceHistoryCommand> context) {
		val command = context.getCommand();
		
		String newHistID = IdentifierUtil.randomUniqueId();
		DateHistoryItem dateItem = new DateHistoryItem(newHistID, new DatePeriod(command.getStartDate(), command.getEndDate()));
		
		AffWorkplaceHistory_ver1 itemtoBeAdded = null;
		
		Optional<AffWorkplaceHistory_ver1> existHist = affWorkplaceHistoryRepository.getAffWorkplaceHistByEmployeeId(command.getEmployeeId());
		
		// In case of exist history of this employee
		if (existHist.isPresent()){
			existHist.get().add(dateItem);
			itemtoBeAdded = existHist.get();
		} else {
			// In case of non - exist history of this employee
			itemtoBeAdded = new AffWorkplaceHistory_ver1(command.getEmployeeId(),new ArrayList<>());
			itemtoBeAdded.add(dateItem);
		}
		
		affWorkplaceHistoryRepository.addAffWorkplaceHistory(itemtoBeAdded);
		
		AffWorkplaceHistoryItem domain = AffWorkplaceHistoryItem.createFromJavaType(newHistID, command.getEmployeeId(), command.getWorkplaceCode(), command.getNormalWorkplaceCode(), command.getLocationCode());
		affWorkplaceHistoryItemRepository.addAffWorkplaceHistory(domain);
		
		return new PeregAddCommandResult(newHistID);
	}
	
}
