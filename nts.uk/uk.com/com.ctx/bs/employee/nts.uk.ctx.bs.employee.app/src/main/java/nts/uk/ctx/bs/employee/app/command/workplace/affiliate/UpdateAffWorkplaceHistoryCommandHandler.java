package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryService;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateAffWorkplaceHistoryCommandHandler extends CommandHandler<UpdateAffWorkplaceHistoryCommand>
	implements PeregUpdateCommandHandler<UpdateAffWorkplaceHistoryCommand>{
	
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;
	
	@Inject
	private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepository;
	
	@Inject 
	private AffWorkplaceHistoryService affWorkplaceHistoryService;
	
	@Override
	public String targetCategoryCd() {
		return "CS00017";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateAffWorkplaceHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateAffWorkplaceHistoryCommand> context) {
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// TODO Get full item (disable, enable, visible, invisible)
		List<ItemValue> fullItems = new ArrayList<>();
		ItemValue itemX = new ItemValue("", "IS00003", "M00000", 1);
		fullItems.add(itemX);
		itemX = new ItemValue("", "IS00004", "MMMMMM", 1);
		fullItems.add(itemX);
		// List item code visible
		List<String> itemVisible = command.getItems().stream().map(ItemValue::itemCode).collect(Collectors.toList());
		
		// List item invisible
		List<ItemValue> itemInvisible = fullItems.stream().filter(i->!itemVisible.contains(i.itemCode())).collect(Collectors.toList());
		
		AnnotationUtil.getStreamOfFieldsAnnotated(UpdateAffWorkplaceHistoryCommand.class, PeregItem.class).forEach(field -> {
			String itemCode = field.getAnnotation(PeregItem.class).value();
			// set item values
			val inputsMap = itemInvisible.stream().collect(Collectors.toMap(item -> item.itemCode(), item -> item));
			val inputItem = inputsMap.get(itemCode);
			if (inputItem != null) {
				if (inputItem.value() != null && field.getType() == String.class) {
					ReflectionUtil.setFieldValue(field, command, inputItem.value().toString());
				} else {
					ReflectionUtil.setFieldValue(field, command, inputItem.value());
				}
			}
		});
		// In case of date period are exist in the screen
		if (command.getStartDate() != null){
			Optional<AffWorkplaceHistory> existHist = affWorkplaceHistoryRepository.getByEmployeeId(companyId, command.getEmployeeId());
			
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
			
			affWorkplaceHistoryService.update(existHist.get(), itemToBeUpdate.get());
		}
		AffWorkplaceHistoryItem histItem = AffWorkplaceHistoryItem.createFromJavaType(command.getHistoryId(), command.getEmployeeId(), command.getWorkplaceId(), command.getNormalWorkplaceId());
		affWorkplaceHistoryItemRepository.update(histItem);
	}
	
}
