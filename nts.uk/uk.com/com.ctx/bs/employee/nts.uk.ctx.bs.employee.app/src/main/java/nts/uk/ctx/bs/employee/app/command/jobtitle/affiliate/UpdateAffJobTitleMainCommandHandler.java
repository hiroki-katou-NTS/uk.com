package nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryService;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItem_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistory_ver1;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;
@Stateless
public class UpdateAffJobTitleMainCommandHandler extends CommandHandler<UpdateAffJobTitleMainCommand>
	implements PeregUpdateCommandHandler<UpdateAffJobTitleMainCommand>{

	@Inject
	private AffJobTitleHistoryRepository_ver1 affJobTitleHistoryRepository_ver1;
	
	@Inject
	private AffJobTitleHistoryItemRepository_ver1 affJobTitleHistoryItemRepository_v1;
	
	@Inject 
	private AffJobTitleHistoryService affJobTitleHistoryService;
	
	@Override
	public String targetCategoryCd() {
		return "CS00016";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateAffJobTitleMainCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateAffJobTitleMainCommand> context) {
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// In case of date period are exist in the screen
		if (command.getStartDate() != null){
			Optional<AffJobTitleHistory_ver1> existHist = affJobTitleHistoryRepository_ver1.getListBySid(companyId, command.getSid());
			
			if (!existHist.isPresent()){
				throw new RuntimeException("invalid AffWorkplaceHistory"); 
			}
			Optional<DateHistoryItem> itemToBeUpdate = existHist.get().getHistoryItems().stream()
	                .filter(h -> h.identifier().equals(command.getHistoryId()))
	                .findFirst();
			
			if (!itemToBeUpdate.isPresent()){
				throw new RuntimeException("invalid AffWorkplaceHistory");
			}
			existHist.get().changeSpan(itemToBeUpdate.get(), new DatePeriod(command.getStartDate(), command.getEndDate()!= null? command.getEndDate():  ConstantUtils.maxDate()));
			
			affJobTitleHistoryService.update(existHist.get(), itemToBeUpdate.get());
		}
		AffJobTitleHistoryItem_ver1 histItem = AffJobTitleHistoryItem_ver1.createFromJavaType(command.getHistoryId(), command.getSid(), command.getJobTitleId(), command.getNote());
		affJobTitleHistoryItemRepository_v1.update(histItem);
	}

}
