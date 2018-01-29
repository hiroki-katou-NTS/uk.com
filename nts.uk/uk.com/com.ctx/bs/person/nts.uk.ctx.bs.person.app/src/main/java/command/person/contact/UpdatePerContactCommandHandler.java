package command.person.contact;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdatePerContactCommandHandler extends CommandHandler<UpdatePerContactCommand>
 	implements PeregUpdateCommandHandler<UpdatePerContactCommand>{

	@Inject
	private PersonEmergencyCtRepository perEmergencyContact;
	
	@Override
	public String targetCategoryCd() {
		return null;
	}

	@Override
	public Class<?> commandClass() {
		return UpdatePerContactCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdatePerContactCommand> context) {
		val command = context.getCommand();
		
		PersonContact perContact = new PersonContact(command.getPersonId(), command.getCellPhoneNumber(),
				command.getMailAdress(), command.getMobileMailAdress(), command.getMemo1(), command.getContactName1(),
				command.getPhoneNumber1(), command.getMemo2(), command.getContactName2(), command.getPhoneNumber2());
		// Update person emergency contact
		
//		perEmergencyContact.updatePersonEmergencyContact(emergencyContact);
	}

}
