package nts.uk.screen.at.ws.processexecution;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kbt002.b.KBT002BQueryProcessor;
import nts.uk.screen.at.app.query.kbt002.b.dto.MasterInfoDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * The class Process execution web service.
 *
 * @author nws-minhnb
 */
@Path("screen/at/processexec")
@Produces("application/json")
public class ProcessExecutionWebService extends WebService {

	/**
	 * The KBT002B query processor.
	 */
	@Inject
	private KBT002BQueryProcessor kbt002bQueryProcessor;

	/**
	 * Gets master info.
	 *
	 * @return the Master info dto
	 */
	@POST
	@Path("getMasterInfo")
	public MasterInfoDto getMasterInfo() {
		return this.kbt002bQueryProcessor.getMasterInfo();
	}

}
