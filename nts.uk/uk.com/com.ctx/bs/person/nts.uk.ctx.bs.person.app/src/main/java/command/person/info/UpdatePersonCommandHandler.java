package command.person.info;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.reflection.AnnotationUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;


@Stateless
public class UpdatePersonCommandHandler extends CommandHandler<UpdatePersonCommand>
	implements PeregUpdateCommandHandler<UpdatePersonCommand>{

	@Inject
	private PersonRepository personRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00002";
	}

	@Override
	public Class<?> commandClass() {
		return UpdatePersonCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdatePersonCommand> context) {
		
		val command = context.getCommand();
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
		
		AnnotationUtil.getStreamOfFieldsAnnotated(UpdatePersonCommand.class, PeregItem.class).forEach(field -> {
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
		Person newPerson = Person.createFromJavaType(command.getBirthDate(),command.getBloodType()!= null?command.getBloodType().intValue(): ConstantUtils.ENUM_UNDEFINE_VALUE,command.getGender()!=null?command.getGender().intValue():ConstantUtils.ENUM_UNDEFINE_VALUE,command.getPersonId(),
				command.getBusinessName(),command.getBusinessNameKana(),command.getPersonName(),command.getPersonNameKana(),command.getBusinessOtherName(),command.getBusinessEnglishName(),
				command.getPersonRomanji(),command.getPersonRomanjiKana(),command.getTodokedeFullName(),command.getTodokedeFullNameKana(),command.getOldName(),command.getOldNameKana(),
				command.getPersonalNameMultilingual(),command.getPersonalNameMultilingualKana());
		
		personRepository.update(newPerson);
	}

}
