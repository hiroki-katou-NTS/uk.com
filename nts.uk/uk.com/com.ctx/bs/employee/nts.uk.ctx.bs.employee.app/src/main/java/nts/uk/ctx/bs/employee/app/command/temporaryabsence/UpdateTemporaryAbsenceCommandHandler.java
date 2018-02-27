package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistoryService;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateTemporaryAbsenceCommandHandler extends CommandHandler<UpdateTemporaryAbsenceCommand>
		implements PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand> {

	@Inject
	private TempAbsItemRepository temporaryAbsenceRepository;

	@Inject
	private TempAbsHistRepository temporaryAbsenceHistRepository;

	@Inject
	private TempAbsHistoryService tempAbsHistoryService;

	@Override
	public String targetCategoryCd() {
		return "CS00018";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateTemporaryAbsenceCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateTemporaryAbsenceCommand> context) {
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
		
		AnnotationUtil.getStreamOfFieldsAnnotated(UpdateTemporaryAbsenceCommand.class, PeregItem.class).forEach(field -> {
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
		// Update history table
		// In case of date period are exist in the screen
		if (command.getStartDate() != null){
			Optional<TempAbsenceHistory> existHist = temporaryAbsenceHistRepository.getByEmployeeId(companyId,
					command.getEmployeeId());
			if (!existHist.isPresent()) {
				throw new RuntimeException("invalid TempAbsenceHistory");
			}
	
			Optional<DateHistoryItem> itemToBeUpdate = existHist.get().getDateHistoryItems().stream()
					.filter(h -> h.identifier().equals(command.getHistoyId())).findFirst();
	
			if (!itemToBeUpdate.isPresent()) {
				throw new RuntimeException("invalid TempAbsenceHistory");
			}
			existHist.get().changeSpan(itemToBeUpdate.get(), new DatePeriod(command.getStartDate(),
					command.getEndDate() != null ? command.getEndDate() : GeneralDate.max()));
			tempAbsHistoryService.update(existHist.get(), itemToBeUpdate.get());
		}
		BigDecimal falseValue = new BigDecimal(0);
		Boolean multiple = null;
		if (command.getMultiple() != null) {
			multiple = falseValue.compareTo(command.getMultiple()) == 0 ? false : true;
		}
		Boolean sameFamily = null;
		if (command.getSameFamily() != null) {
			sameFamily = falseValue.compareTo(command.getSameFamily()) == 0 ? false : true;
		}
		Boolean spouseIsLeave = null;
		if (command.getSpouseIsLeave() != null) {
			spouseIsLeave = falseValue.compareTo(command.getSpouseIsLeave()) == 0 ? false : true;
		}

		// Update detail table
		int tempAbsenceFrNo = command.getTempAbsenceFrNo() != null ? command.getTempAbsenceFrNo().intValue() : 0;
		Integer soInsPayCategory = command.getSoInsPayCategory() != null ? command.getSoInsPayCategory().intValue()
				: null;
		Integer childType = command.getChildType() != null ? command.getChildType().intValue() : null;
		Integer sameFamilyDays = command.getSameFamilyDays() != null ? command.getSameFamilyDays().intValue() : null;

		TempAbsenceHisItem temporaryAbsence = TempAbsenceHisItem.createTempAbsenceHisItem(tempAbsenceFrNo,
				command.getHistoyId(), command.getEmployeeId(), command.getRemarks(), soInsPayCategory, multiple,
				command.getFamilyMemberId(), sameFamily, childType, command.getCreateDate(), spouseIsLeave,
				sameFamilyDays);
		temporaryAbsenceRepository.update(temporaryAbsence);
	}

}
