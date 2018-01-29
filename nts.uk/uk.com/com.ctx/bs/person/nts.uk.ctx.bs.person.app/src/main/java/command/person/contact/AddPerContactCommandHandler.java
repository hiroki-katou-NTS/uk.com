package command.person.contact;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddPerContactCommandHandler extends CommandHandlerWithResult<AddPerContactCommand,PeregAddCommandResult>
 	implements PeregAddCommandHandler<AddPerContactCommand>{

	@Inject
	private PersonEmergencyCtRepository perEmergencyContact;
	
	@Override
	public String targetCategoryCd() {
		return null;
	}

	@Override
	public Class<?> commandClass() {
		return AddPerContactCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddPerContactCommand> context) {
		val command = context.getCommand();
		// Create new id
		String newId = IdentifierUtil.randomUniqueId();
		
		PersonContact perContact = new PersonContact(command.getPersonId(), command.getCellPhoneNumber(),
				command.getMailAdress(), command.getMobileMailAdress(), command.getMemo1(), command.getContactName1(),
				command.getPhoneNumber1(), command.getMemo2(), command.getContactName2(), command.getPhoneNumber2());
		
		// Add person emergency contact
		
//		perEmergencyContact.addPersonEmergencyContact(emergencyContact);
		
		return new PeregAddCommandResult(newId);
	}

}
