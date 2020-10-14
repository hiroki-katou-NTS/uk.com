package nts.uk.ctx.sys.portal.ws.generalsearch;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.generalsearch.DeleteGeneralSearchHistoryCommandHandler;
import nts.uk.ctx.sys.portal.app.command.generalsearch.GeneralSearchHistoryCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.WebMenuCommandBase;

/**
 * The Class WebGeneralSearchHistoryService.
 */
@Path("sys/portal/generalsearch/history")
@Produces("application/json")
public class WebGeneralSearchHistoryService extends WebService {
	@Inject
	private DeleteGeneralSearchHistoryCommandHandler deleteHandler;
	
	/**
	 * Adds the.
	 *
	 * @param command the command
	 */
	@POST
	@Path("add")
	public void add(WebMenuCommandBase command) {
//		this.addWebMenuCommandHandler.handle(command);
	}

	/**
	 * Update.
	 *
	 * @param command the command
	 */
	@POST
	@Path("update")
	public void update(WebMenuCommandBase command) {
//		this.updateWebMenuCommandHander.handle(command);
	}

	/**
	 * Removes the.
	 * 削除する
	 * @param command the command
	 */
	@POST
	@Path("remove")
	public void remove(GeneralSearchHistoryCommand command) {
		this.deleteHandler.handle(command);
	}
	
}
