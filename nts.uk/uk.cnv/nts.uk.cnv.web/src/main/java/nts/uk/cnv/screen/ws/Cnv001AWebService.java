package nts.uk.cnv.screen.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.screen.app.query.Cnv001AService;
import nts.uk.cnv.screen.app.query.dto.Cnv001ALoadDataDto;
import nts.uk.cnv.screen.ws.dto.Cnv001ALoadDataParam;

@Path("cnv/cnv001a")
@Produces("application/json")
public class Cnv001AWebService extends WebService {

	@Inject
	private Cnv001AService screenService;

	@POST
	@Path("loaddata")
	public Cnv001ALoadDataDto loadData(Cnv001ALoadDataParam param) {
		return screenService.loadData(param.getCategoryName().replace("\"", ""), param.getTableName().replace("\"", ""));
	}
}
