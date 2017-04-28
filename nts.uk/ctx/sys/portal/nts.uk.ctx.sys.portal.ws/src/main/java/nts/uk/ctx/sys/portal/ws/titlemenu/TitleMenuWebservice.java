/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.ws.titlemenu;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.titlemenu.CreateTitleMenuCommand;
import nts.uk.ctx.sys.portal.app.command.titlemenu.CreateTitleMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.titlemenu.DeleteTitleMenuCommand;
import nts.uk.ctx.sys.portal.app.command.titlemenu.DeleteTitleMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.titlemenu.UpdateTitleMenuCommand;
import nts.uk.ctx.sys.portal.app.command.titlemenu.UpdateTitleMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.find.titlemenu.TitleMenuDto;
import nts.uk.ctx.sys.portal.app.find.titlemenu.TitleMenuFinder;

@Path("sys/portal/titlemenu")
@Produces("application/json")
public class TitleMenuWebservice extends WebService {

	@Inject
	private CreateTitleMenuCommandHandler createTitleMenu;

    @Inject
	private DeleteTitleMenuCommandHandler deleteTitleMenu;

	@Inject
	private UpdateTitleMenuCommandHandler updateTitleMenu;
	
	@Inject
	private TitleMenuFinder finder;

	@POST
	@Path("findall")
	public List<TitleMenuDto> getAllTitleMenu(){
		return this.finder.getAllTitleMenu();
	}
	
	@POST
	@Path("findbycode")
	public void getByCode(@PathParam("companyID") String titleMenuCD){
		this.finder.getTitleMenu(titleMenuCD);
			
	}

	@POST
	@Path("create")
	public void createTitleMenu(CreateTitleMenuCommand command) {
		this.createTitleMenu.handle(command);
	}

	@POST
	@Path("delete")
	public void deleteTitleMenu(DeleteTitleMenuCommand command) {
		this.deleteTitleMenu.handle(command);
	}
	@POST
	@Path("update")
	public void updateTitleMenu(UpdateTitleMenuCommand command) {
		this.updateTitleMenu.handle(command);
	}

}
