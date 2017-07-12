package nts.uk.ctx.sys.portal.ws.webmenu;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.personaltying.AddPersonalTyingCommand;
import nts.uk.ctx.sys.portal.app.command.personaltying.AddPersonalTyingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.personaltying.DeletePersonalTyingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.personaltying.PersonTypingCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.AddWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.AddWebMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.CopyWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.CopyWebMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.RemoveWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.RemoveWebMenuCommandHander;
import nts.uk.ctx.sys.portal.app.command.webmenu.UpdateWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.UpdateWebMenuCommandHander;
import nts.uk.ctx.sys.portal.app.find.webmenu.EditMenuBarDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.PersonTypeDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.WebMenuDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.WebMenuFinder;

@Path("sys/portal/webmenu")
@Produces("application/json")
public class WebMenuWebService extends WebService {

	@Inject
	private WebMenuFinder webMenuFinder;

	@Inject
	private AddWebMenuCommandHandler addWebMenuCommandHandler;

	@Inject
	private UpdateWebMenuCommandHander updateWebMenuCommandHander;

	@Inject
	private RemoveWebMenuCommandHander removeWebMenuCommandHander;
	
	@Inject
	private CopyWebMenuCommandHandler copyWebMenuCommandHander;
	
	@Inject
	private AddPersonalTyingCommandHandler addPersonTypeCommandHandler;
	
	@Inject
	private DeletePersonalTyingCommandHandler deletePersonTypeCommandHandler;

	@POST
	@Path("add")
	public void add(AddWebMenuCommand command) {
		this.addWebMenuCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdateWebMenuCommand command) {
		this.updateWebMenuCommandHander.handle(command);
	}

	@POST
	@Path("remove")
	public void remove(RemoveWebMenuCommand command) {
		this.removeWebMenuCommandHander.handle(command);
	}

	@POST
	@Path("find")
	public List<WebMenuDto> findAll() {
		return this.webMenuFinder.findAll();
	}
	
	@POST
	@Path("find/{webMenuCode}")
	public WebMenuDto find(@PathParam("webMenuCode") String webMenuCode) {
		return this.webMenuFinder.find(webMenuCode);
	}
	
	@POST
	@Path("edit")
	public EditMenuBarDto editMenuBar() {
		return this.webMenuFinder.getEditMenuBarDto();
	}
	
	@POST
	@Path("copy")
	public void copyWebMenu(CopyWebMenuCommand command) {
		this.copyWebMenuCommandHander.handle(command);
	}

	
	@POST
	@Path("addPerson")
	public void addPerson(List<PersonTypingCommand> command) {
		this.addPersonTypeCommandHandler.handle(command);
	}
	@POST
	@Path("deletePerson")
	public void deletePerson(AddPersonalTyingCommand command) {
		this.deletePersonTypeCommandHandler.handle(command);
	}
	
	@POST
	@Path("findPerson/{employeeId}")
	public List<PersonTypeDto> findAllPerson(@PathParam("employeeId") String employeeId) {
		return this.webMenuFinder.findAllPerson(employeeId);
	}
	
}
