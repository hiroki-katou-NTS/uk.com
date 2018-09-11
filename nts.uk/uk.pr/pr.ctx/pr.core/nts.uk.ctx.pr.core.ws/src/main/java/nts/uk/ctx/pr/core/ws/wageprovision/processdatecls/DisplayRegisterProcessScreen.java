package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.InitialDisplayRegisterProcessingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.InitialDisplayRegisterProcessingFinder;

@Path("ctx.pr.core.ws.wageprovision.processdatecls")
@Produces("application/json")
public class DisplayRegisterProcessScreen {
	@Inject
	private InitialDisplayRegisterProcessingFinder finder;

	@POST
	@Path("findDisplayRegister/{processingCategoryNo}")
	public InitialDisplayRegisterProcessingDto findDisplayRegister(
			@PathParam("processingCategoryNo") int processCateNo) {
		return finder.getProcessInformationByProcessingCategoryNo(processCateNo);
	}
}
