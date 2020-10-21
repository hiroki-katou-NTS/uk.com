package nts.uk.ctx.sys.portal.ws.generalsearch;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.generalsearch.AddGeneralSearchHistoryCommandHandler;
import nts.uk.ctx.sys.portal.app.command.generalsearch.DeleteGeneralSearchHistoryCommandHandler;
import nts.uk.ctx.sys.portal.app.command.generalsearch.GeneralSearchHistoryCommand;
import nts.uk.ctx.sys.portal.app.command.generalsearch.UpdateGeneralSearchHistoryCommandHandler;
import nts.uk.ctx.sys.portal.app.find.generalsearch.GeneralSearchHistoryDto;
import nts.uk.ctx.sys.portal.app.find.generalsearch.GeneralSearchHistoryFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WebGeneralSearchHistoryService.
 */
@Path("sys/portal/generalsearch/history")
@Produces("application/json")
public class WebGeneralSearchHistoryService extends WebService {
	
	/** The delete handler. */
	@Inject
	private DeleteGeneralSearchHistoryCommandHandler deleteHandler;
	
	/** The insert handler. */
	@Inject
	private AddGeneralSearchHistoryCommandHandler insertHandler;
	
	/** The update handler. */
	@Inject
	private UpdateGeneralSearchHistoryCommandHandler updateHandler;
	
	@Inject
	private GeneralSearchHistoryFinder finder;
	
	/**
	 * Save the GeneralSearchHistoryCommand.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(GeneralSearchHistoryCommand command) {
		List<GeneralSearchHistoryDto> listResults = this.getByContents(command.getSearchCategory(), command.getContents());
		if (listResults.isEmpty()) {
			this.insertHandler.handle(command);
		} else {
			this.updateHandler.handle(command);
		}
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
	
	/**
	 * Gets the 10 last result.
	 *
	 * @param searchCategory the search category
	 * @return the 10 last result
	 */
	@POST
	@Path("get-10-last-result/{searchCategory}")
	public List<GeneralSearchHistoryDto> get10LastResult(@PathParam("searchCategory") int searchCategory) {
		return this.finder.getLast10UsedSearches(AppContexts.user().userId(), AppContexts.user().companyId(), searchCategory);
	}
	
	/**
	 * Gets the by contents.
	 *
	 * @param searchCategory the search category
	 * @param searchContent the search content
	 * @return the by contents
	 */
	@POST
	@Path("get-by-content/{searchCategory}/{searchContent}")
	public List<GeneralSearchHistoryDto> getByContents(@PathParam("searchCategory") int searchCategory, @PathParam("searchContent") String searchContent) {
		return this.finder.getByContents(AppContexts.user().userId(), AppContexts.user().companyId(), searchCategory, searchContent);
	}
	
}
