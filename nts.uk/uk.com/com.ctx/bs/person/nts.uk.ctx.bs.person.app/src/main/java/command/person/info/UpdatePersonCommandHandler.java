package command.person.info;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
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
		// Person name, person name kana, bussiness name, bussiness name kana 氏名には、文字の間に必ず全角スペースがなければならない。
		if (!ValidateUtils.validateName(command.getPersonName()) || !ValidateUtils.validateName(command.getPersonNameKana())
				|| !ValidateUtils.validateName(command.getBusinessName()) || !ValidateUtils.validateName(command.getBusinessNameKana())){
			throw new BusinessException("Msg_924");
		}
		Person newPerson = Person.createFromJavaType(command.getBirthDate(),command.getBloodType()!= null?command.getBloodType().intValue(): ConstantUtils.ENUM_UNDEFINE_VALUE,command.getGender()!=null?command.getGender().intValue():ConstantUtils.ENUM_UNDEFINE_VALUE,command.getPersonId(),
				command.getBusinessName(),command.getBusinessNameKana(),command.getPersonName(),command.getPersonNameKana(),command.getBusinessOtherName(),command.getBusinessEnglishName(),
				command.getPersonRomanji(),command.getPersonRomanjiKana(),command.getTodokedeFullName(),command.getTodokedeFullNameKana(),command.getOldName(),command.getOldNameKana(),
				command.getPersonalNameMultilingual(),command.getPersonalNameMultilingualKana());
		
		personRepository.update(newPerson);
	}

}
