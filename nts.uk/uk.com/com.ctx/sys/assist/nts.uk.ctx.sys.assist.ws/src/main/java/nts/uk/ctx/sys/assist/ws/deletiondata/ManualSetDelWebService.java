/**
 * 
 */
package nts.uk.ctx.sys.assist.ws.deletiondata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting.AddManualSetDelHandler;
import nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting.ManualSetDelCommand;

/**
 * @author nam.lh
 *
 */
@Path("ctx/sys/assist/app")
@Produces("application/json")
public class ManualSetDelWebService extends WebService {

	@Inject
	private AddManualSetDelHandler addMalSet;

//	@Inject
//	private ManualSettingFinder manualSettingFinder;

	@POST
	@Path("addManualSetDel")
	public void add(ManualSetDelCommand mal) {
		this.addMalSet.handle(mal);
	}

//	@POST
//	@Path("findAll")
//	public List<ManualSettingDto> getAll() {
//		return manualSettingFinder.getAll();
//	}
}
