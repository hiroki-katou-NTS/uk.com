package nts.uk.screen.at.ws.processexecution;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kbt002.b.KBT002BQueryProcessor;
import nts.uk.screen.at.app.query.kbt002.b.dto.MasterInfoDto;

@Path("screen/at/processexec")
@Produces("application/json")
public class ProcessExecutionWebService extends WebService {

	@Inject
	private KBT002BQueryProcessor kbt002bQueryProcessor;

	@POST
	@Path("getMasterInfo")
	public MasterInfoDto getMasterInfo() {
		return this.kbt002bQueryProcessor.getMasterInfo();
	}

}
