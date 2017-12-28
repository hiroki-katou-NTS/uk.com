/**
 * 
 */
package nts.uk.ctx.bs.employee.app.command.classification.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItem_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepositoryService;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

/**
 * @author danpv
 * @author hop.nt
 *
 */
@Stateless
public class UpdateAffClassCommandHandler extends CommandHandler<UpdateAffClassificationCommand>
		implements PeregUpdateCommandHandler<UpdateAffClassificationCommand> {

	@Inject
	private AffClassHistoryRepository_ver1 affClassHistoryRepo;

	@Inject
	private AffClassHistItemRepository_ver1 affClassHistItemRepo;
	
	@Inject
	private AffClassHistoryRepositoryService affClassHistoryRepositoryService;

	@Override
	public String targetCategoryCd() {
		return "CS00004";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateAffClassificationCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateAffClassificationCommand> context) {
		UpdateAffClassificationCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// In case of date period are exist in the screen
		if (command.getStartDate() != null){
			// update history
			Optional<AffClassHistory_ver1> historyOption = affClassHistoryRepo.getByEmployeeId(companyId, command.getEmployeeId());
			if (!historyOption.isPresent()) {
				throw new RuntimeException("invalid AffClassHistory_ver1");
			}
	
			Optional<DateHistoryItem> itemToBeUpdateOpt = historyOption.get().getPeriods().stream()
					.filter(h -> h.identifier().equals(command.getHistoryId())).findFirst();
			if (!itemToBeUpdateOpt.isPresent()) {
				throw new RuntimeException("invalid AffClassHistory_ver1");
			}
	
			historyOption.get().changeSpan(itemToBeUpdateOpt.get(),
					new DatePeriod(command.getStartDate(), command.getEndDate()!= null? command.getEndDate():  ConstantUtils.maxDate()));
			affClassHistoryRepositoryService.update(historyOption.get(), itemToBeUpdateOpt.get());
		}
		// update history item
		AffClassHistItem_ver1 historyItem = AffClassHistItem_ver1.createFromJavaType(command.getEmployeeId(),
				command.getHistoryId(), command.getClassificationCode());
		affClassHistItemRepo.update(historyItem);

	}

}
