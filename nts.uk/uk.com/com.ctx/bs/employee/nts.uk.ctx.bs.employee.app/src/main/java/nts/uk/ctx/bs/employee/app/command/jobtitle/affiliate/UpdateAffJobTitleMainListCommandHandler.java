package nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryImmediately;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryService;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateAffJobTitleMainListCommandHandler extends CommandHandler<List<UpdateAffJobTitleMainCommand>>
implements PeregUpdateListCommandHandler<UpdateAffJobTitleMainCommand>{
	@Inject
	private AffJobTitleHistoryRepository affJobTitleHistoryRepository;
	
	@Inject
	private AffJobTitleHistoryItemRepository affJobTitleHistoryItemRepository;
	
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
	protected void handle(CommandHandlerContext<List<UpdateAffJobTitleMainCommand>> context) {
		List<UpdateAffJobTitleMainCommand> command = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<AffJobTitleHistoryImmediately> immedidately = new ArrayList<>();
		List<AffJobTitleHistoryItem> histItems = new ArrayList<>();
		List<String> sids = command.parallelStream().map(c -> c.getSid()).collect(Collectors.toList());

		Map<String, List<AffJobTitleHistory>> existHistMap = affJobTitleHistoryRepository.getListBySids(cid, sids)
				.parallelStream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		command.parallelStream().forEach(c ->{
			if (c.getStartDate() != null){
				List<AffJobTitleHistory> affJobTitleHistoryLst = existHistMap.get(c.getSid());
				if(affJobTitleHistoryLst != null) {
					AffJobTitleHistory affJobTitleHistory = affJobTitleHistoryLst.get(0);
					Optional<DateHistoryItem> itemToBeUpdate = affJobTitleHistory.getHistoryItems().stream()
			                .filter(h -> h.identifier().equals(c.getHistoryId()))
			                .findFirst();
					if (itemToBeUpdate.isPresent()){
						affJobTitleHistory.changeSpan(itemToBeUpdate.get(), new DatePeriod(c.getStartDate(), c.getEndDate()!= null? c.getEndDate():  ConstantUtils.maxDate()));
						immedidately.add(new AffJobTitleHistoryImmediately(affJobTitleHistory, itemToBeUpdate.get()));
					}
				}
				AffJobTitleHistoryItem histItem = AffJobTitleHistoryItem.createFromJavaType(c.getHistoryId(), c.getSid(), c.getJobTitleId(), c.getNote());
				histItems.add(histItem);
			}
		});
		
		if(!immedidately.isEmpty()) {
			affJobTitleHistoryService.updateAll(immedidately);
		}
		
		if(!histItems.isEmpty()) {
			affJobTitleHistoryItemRepository.updateAll(histItems);
		}

		
	}

}
