package nts.uk.ctx.basic.ws.organization.workplace;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.organization.workplace.AddWorkPlaceCommandHandler;
import nts.uk.ctx.basic.app.command.organization.workplace.RemoveWorkPlaceCommandHandler;
import nts.uk.ctx.basic.app.command.organization.workplace.UpdateWorkPlaceCommandHandler;
import nts.uk.ctx.basic.app.find.organization.workplace.WorkPlaceDto;
import nts.uk.ctx.basic.app.find.organization.workplace.WorkPlaceFinder;
import nts.uk.ctx.basic.app.find.organization.workplace.WorkPlaceMemoDto;
import nts.uk.ctx.basic.app.find.organization.workplace.WorkPlaceQueryResult;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.shr.com.context.AppContexts;

@Path("basic/organization")
@Produces("application/json")
public class WorkplaceWebService extends WebService {

	@Inject
	private WorkPlaceFinder finder;

	@Inject
	private AddWorkPlaceCommandHandler addWorkPlace;

	@Inject
	private UpdateWorkPlaceCommandHandler updateWorkPlace;

	@Inject
	private RemoveWorkPlaceCommandHandler removeWorkPlace;

	@POST
	@Path("getallworkplace")
	public WorkPlaceQueryResult init() {
		String ccd = AppContexts.user().companyCode();
		WorkPlaceQueryResult i = finder.handle();
		System.out.println(i);
		return i;
	}

	@POST
	@Path("getallwkpbyhistid/{historyId}")
	public List<WorkPlaceDto> getListWkpByHistId(@PathParam("historyId") String historyId) {
		String ccd = AppContexts.user().companyCode();
		List<WorkPlaceDto> list = finder.findAllByHistory(ccd, historyId);
		System.out.println(list);
		return list;
	}

	@POST
	@Path("getmemowkpbyhistid/{historyId}")
	public WorkPlaceMemoDto getMemoByHistId(@PathParam("historyId") String historyId) {
		String ccd = AppContexts.user().companyCode();
		return finder.findMemo(ccd, historyId).get();
	}

}
