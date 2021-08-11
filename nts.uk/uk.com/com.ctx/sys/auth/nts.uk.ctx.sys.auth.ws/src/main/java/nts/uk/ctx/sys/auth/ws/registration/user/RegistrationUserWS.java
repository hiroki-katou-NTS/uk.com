package nts.uk.ctx.sys.auth.ws.registration.user;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.auth.app.command.registration.user.AddRegistrationUserCommand;
import nts.uk.ctx.sys.auth.app.command.registration.user.AddRegistrationUserCommandHandler;
import nts.uk.ctx.sys.auth.app.command.registration.user.DeleteRegistrationUserCommand;
import nts.uk.ctx.sys.auth.app.command.registration.user.DeleteRegistrationUserCommandHandler;
import nts.uk.ctx.sys.auth.app.command.registration.user.UpdateRegistrationUserCommand;
import nts.uk.ctx.sys.auth.app.command.registration.user.UpdateRegistrationUserCommandHandler;
import nts.uk.ctx.sys.auth.app.find.registration.user.CompanyImportDto;
import nts.uk.ctx.sys.auth.app.find.registration.user.RegistrationUserFinder;
import nts.uk.ctx.sys.auth.app.find.registration.user.UserDto;

/**
 * The Class RegistrationUserWS.
 */
@Path("ctx/sys/auth/regis/user")
@Produces("application/json")
public class RegistrationUserWS extends WebService {

	/** The registration user finder. */
	@Inject
	private RegistrationUserFinder registrationUserFinder;

	/** The add registration user command handler. */
	@Inject
	private AddRegistrationUserCommandHandler addRegistrationUserCommandHandler;

	/** The delete registration user command handler. */
	@Inject
	private DeleteRegistrationUserCommandHandler deleteRegistrationUserCommandHandler;

	/** The update registration user command handler. */
	@Inject
	private UpdateRegistrationUserCommandHandler updateRegistrationUserCommandHandler;

	/**
	 * Find company import list.
	 *
	 * @return the list
	 */
	@POST
	@Path("getAllCom")
	public List<CompanyImportDto> findCompanyImportList() {
		return this.registrationUserFinder.getCompanyImportList();
	}

	/**
	 * Gets the list user.
	 *
	 * @param cid the cid
	 * @return the list user
	 */
	@POST
	@Path("getlistUser/{cid}")
	public List<UserDto> getListUser(@PathParam("cid") String cid) {
		return this.registrationUserFinder.getLoginUserListByCurrentCID(cid);
	}

	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	@POST
	@Path("getAllUser")
	public List<UserDto> getAllUser() {
		return this.registrationUserFinder.getLoginUserListByContractCode();
	}

	/**
	 * Register user.
	 *
	 * @param command the command
	 * @return the string
	 */
	@POST
	@Path("register")
	public JavaTypeResult<String> registerUser(AddRegistrationUserCommand command) {
		return new JavaTypeResult<String>(addRegistrationUserCommandHandler.handle(command));
	}

	/**
	 * Update user.
	 *
	 * @param command the command
	 * @return the string
	 */
	@POST
	@Path("update")
	public void updateUser(UpdateRegistrationUserCommand command) {
		this.updateRegistrationUserCommandHandler.handle(command);
	}

	/**
	 * Delete.
	 *
	 * @param command the command
	 * @return the string
	 */
	@POST
	@Path("delete")
	public void delete(DeleteRegistrationUserCommand command) {
		this.deleteRegistrationUserCommandHandler.handle(command);
	}

}
