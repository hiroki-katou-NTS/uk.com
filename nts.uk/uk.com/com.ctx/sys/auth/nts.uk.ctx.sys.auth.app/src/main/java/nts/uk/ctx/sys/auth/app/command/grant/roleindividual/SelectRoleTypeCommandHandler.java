package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
@Transactional
public class SelectRoleTypeCommandHandler extends CommandHandler<SelectRoleTypeCommand> {

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepository;

	@Inject
	private UserRepository userRepository;
	
	@Inject
	private PersonAdapter personAdapter;

	@Override
	protected void handle(CommandHandlerContext<SelectRoleTypeCommand> context) {

		SelectRoleTypeCommand command = context.getCommand();
		List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepository.findByCompanyIdAndRoleType(command.getCompanyID(), command.getRoleType().value);
		// Other than above Company ID = Max digit 0
		List<String> listUserID = listRoleIndividualGrant.stream().map(c -> c.getUserId()).collect(Collectors.toList());

		List<User> listUser = userRepository.getByListUser(listUserID);

		List<String> listAssPersonID = listUser.stream().map(c -> c.getAssociatedPersonID()).collect(Collectors.toList());
		
		if(!listAssPersonID.isEmpty()){
			List<PersonImport> listPerson = personAdapter.findByPersonIds(listAssPersonID);
				
					
					 
					 
					
		}

	}

}
