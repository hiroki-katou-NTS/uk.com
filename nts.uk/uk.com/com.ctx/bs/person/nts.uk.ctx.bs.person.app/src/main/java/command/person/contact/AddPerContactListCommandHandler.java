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
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddPerContactListCommandHandler extends CommandHandlerWithResult<List<AddPerContactCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddPerContactCommand>{
	@Inject
	private PersonContactRepository personContactRepository;
	@Override
	public String targetCategoryCd() {
		return "CS00022";
	}

	@Override
	public Class<?> commandClass() {
		return AddPerContactCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddPerContactCommand>> context) {
		List<AddPerContactCommand> cmd = context.getCommand();
		List<PersonContact> domains = cmd.stream().map(c ->{ return new PersonContact(c.getPersonId(), c.getCellPhoneNumber(),
				c.getMailAdress(), c.getMobileMailAdress(), c.getMemo1(), c.getContactName1(),
				c.getPhoneNumber1(), c.getMemo2(), c.getContactName2(), c.getPhoneNumber2());}).collect(Collectors.toList());
		if(!domains.isEmpty()) {
			personContactRepository.addAll(domains);
		}
		return new ArrayList<>();
	}

}
