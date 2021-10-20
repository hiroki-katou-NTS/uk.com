package nts.uk.cnv.screen.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.screen.app.query.Cnv001BService;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadDataDto;
import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadParamDto;

@Path("cnv/cnv001b")
@Produces("application/json")
public class Cnv001BWebService extends WebService {

	@Inject
	private Cnv001BService screenService;

	@POST
	@Path("loaddata")
	public Cnv001BLoadDataDto getUkColumns(Cnv001BLoadParamDto param) {
		return screenService.loadData(param);
	}
}
