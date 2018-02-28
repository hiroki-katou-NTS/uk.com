/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.exio.ws.exi.execlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacExeResultLogDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExacExeResultLogFinder;

/**
 * The Class WorkplaceConfigInfoWebService
 */
@Path("ctx/exio/ws/exi/execlog")
@Produces("application/json")
public class ExiExecLogWebService extends WebService {
	@Inject
	private ExacExeResultLogFinder exacExeResultLogFinder;
	
	/**
	 * @param externalProcessId
	 * @return
	 */
	@Path("getLogResults")
	@POST
	public List<ExacExeResultLogDto> getLogResults(String externalProcessId) {
		return this.exacExeResultLogFinder.getExacExeResultLogByProcessId(externalProcessId);
	}
	
}
