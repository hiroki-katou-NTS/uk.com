package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ReflectSystemReferenceDateInfoDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ReflectSystemReferenceDateInfoFinder;

public class ReflectSystemReferenceDateInfoWebService {
	@Inject
	private ReflectSystemReferenceDateInfoFinder finder;

	@POST
	@Path("findReflectSystemReferenceDateInfo/{processingCategoryNo}/{processDate}")
	public ReflectSystemReferenceDateInfoDto findReflectSystemReferenceDateInfo(@PathParam("processingCategoryNo") int processCateNo,
			@PathParam("processDate") int processDate) {
		return finder.getReflectSystemReferenceDateInfoDto(processCateNo, processDate);
	}
}
