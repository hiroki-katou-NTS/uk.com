package nts.uk.ctx.sys.portal.ws.webmenu;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.webmenu.AddPersonalTyingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.AddWebMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.CopyWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.CopyWebMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.PersonTypingCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.RemoveWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.RemoveWebMenuCommandHander;
import nts.uk.ctx.sys.portal.app.command.webmenu.UpdateWebMenuCommandHander;
import nts.uk.ctx.sys.portal.app.command.webmenu.WebMenuCommandBase;
import nts.uk.ctx.sys.portal.app.find.webmenu.EditMenuBarDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.PersonTypeDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.WebMenuDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.WebMenuFinder;
import nts.uk.ctx.sys.portal.app.find.webmenu.detail.WebMenuDetailDto;

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
	

	@POST
	@Path("add")
	public void add(WebMenuCommandBase command) {
		this.addWebMenuCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(WebMenuCommandBase command) {
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
	@Path("finddefault")
	public WebMenuDetailDto findDefault() {
		return this.webMenuFinder.findDefault();
	}
	
	@POST
	@Path("program")
	public JavaTypeResult<String> getProgramName() {
		return new JavaTypeResult<String>(this.webMenuFinder.getProgram());
	}
	
	@POST
	@Path("finddetails")
	public List<WebMenuDetailDto> findAllDetails() {
		return this.webMenuFinder.findAllDetails();
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
	public void addPerson(PersonTypingCommand command) {
 		this.addPersonTypeCommandHandler.handle(command);
	}
	
	@POST
	@Path("findPerson/{employeeId}")
	public List<PersonTypeDto> findAllPerson(@PathParam("employeeId") String employeeId) {
		return this.webMenuFinder.findAllPerson(employeeId);
	}
	
	@POST
	@Path("companies")
	public List<String> companies() {
		return Arrays.asList("日通システム株式会社", "KSB", "日通システムベトナム");
	}
	
	@POST
	@Path("username")
	public JavaTypeResult<String> userName() {
		return new JavaTypeResult<String>("日通　太郎");
	}
	
}
