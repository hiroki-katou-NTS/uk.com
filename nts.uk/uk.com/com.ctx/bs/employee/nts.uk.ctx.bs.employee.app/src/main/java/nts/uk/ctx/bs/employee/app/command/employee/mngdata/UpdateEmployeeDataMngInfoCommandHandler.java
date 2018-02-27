package nts.uk.ctx.bs.employee.app.command.employee.mngdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateEmployeeDataMngInfoCommandHandler extends CommandHandler<UpdateEmployeeDataMngInfoCommand>
	implements PeregUpdateCommandHandler<UpdateEmployeeDataMngInfoCommand>{
	
	@Inject
	private EmployeeDataMngInfoRepository employeeDataMngInfoRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00001";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeDataMngInfoCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateEmployeeDataMngInfoCommand> context) {
		
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
		
		AnnotationUtil.getStreamOfFieldsAnnotated(UpdateEmployeeDataMngInfoCommand.class, PeregItem.class).forEach(field -> {
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
		
		// 同じ会社IDかつ、削除状況＝削除していないものは、社員コードは重複してはいけない （#Msg_345#）
		Optional<EmployeeDataMngInfo> employeeData = employeeDataMngInfoRepository.findByEmployeCD(command.getEmployeeCode(), companyId);
		
		if (employeeData.isPresent() && !employeeData.get().getEmployeeId().equals(command.getEmployeeId())){
			throw new BusinessException("Msg_345");
		}
		
		EmployeeDataMngInfo domain = new EmployeeDataMngInfo(companyId,command.getPersonId(), command.getEmployeeId(),command.getEmployeeCode(),command.getExternalCode());
		
		employeeDataMngInfoRepository.update(domain);
	}

}
