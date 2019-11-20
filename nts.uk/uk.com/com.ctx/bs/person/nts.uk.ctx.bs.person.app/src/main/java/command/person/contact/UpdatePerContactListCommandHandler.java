package command.person.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdatePerContactListCommandHandler  extends CommandHandlerWithResult<List<UpdatePerContactCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdatePerContactCommand>{
	@Inject
	private PersonContactRepository personContactRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00022";
	}

	@Override
	public Class<?> commandClass() {
		return UpdatePerContactCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdatePerContactCommand>> context) {
		List<UpdatePerContactCommand> cmd = context.getCommand();
		List<PersonContact> domains = cmd.stream().map(c ->{ return new PersonContact(c.getPersonId(), c.getCellPhoneNumber(),
				c.getMailAdress(), c.getMobileMailAdress(), c.getMemo1(), c.getContactName1(),
				c.getPhoneNumber1(), c.getMemo2(), c.getContactName2(), c.getPhoneNumber2());}).collect(Collectors.toList());
		// Update person emergency contact
		personContactRepository.updateAll(domains);
		return new ArrayList<MyCustomizeException>();
	}

}
