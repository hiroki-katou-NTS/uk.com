package nts.uk.ctx.sys.portal.ws.generalsearch;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.dom.generalsearch.service.GeneralSearchHistoryService;

@Path("sys/portal/generalsearch")
@Produces("application/json")
public class WebGeneralSearchService extends WebService {

	@Inject
	private GeneralSearchHistoryService service;
	
	/**
	 * Can search.
	 * マニュアル検索できる
	 * @return true, if successful
	 */
	public boolean canSearch() {
		return this.service.checkRoleSearchManual();
	}
}
