package command.person.info;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
		return "CS00001";
	}

	@Override
	public Class<?> commandClass() {
		return UpdatePersonCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdatePersonCommand> context) {
		
		val command = context.getCommand();
		
		Person newPerson = Person.createFromJavaType(command.getBirthDate(),command.getBloodType(),command.getGender(),command.getPersonId(),command.getMailAddress(),
				command.getPersonMobile(),command.getBusinessName(),command.getPersonName(),command.getBusinessOtherName(),command.getBusinessEnglishName(),command.getPersonNameKana(),
				command.getPersonRomanji(),command.getPersonRomanjiKana(),command.getTodokedeFullName(),command.getTodokedeFullNameKana(),command.getOldName(),command.getOldNameKana(),
				command.getTodokedeOldFullName(),command.getTodokedeOldFullNameKana(),command.getHobby(),command.getCountryId(),command.getTaste());
		
		personRepository.updatePerson(newPerson);
	}

}
