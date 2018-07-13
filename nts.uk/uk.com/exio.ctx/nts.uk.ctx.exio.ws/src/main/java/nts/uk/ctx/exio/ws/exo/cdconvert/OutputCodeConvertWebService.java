package nts.uk.ctx.exio.ws.exo.cdconvert;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exo.cdconvert.OutputCodeConvertDTO;
import nts.uk.ctx.exio.app.find.exo.cdconvert.OutputCodeConvertFinder;

@Path("exio/exo/codeconvert")
@Produces("application/json")
public class OutputCodeConvertWebService extends WebService {

	@Inject
	private OutputCodeConvertFinder outputCodeConvertFinder;

	@POST
	@Path("getOutputCodeConvertByCompanyId")
	public List<OutputCodeConvertDTO> getOutputCodeConvertByCid() {
		return this.outputCodeConvertFinder.getOutputCodeConvertByCid();
	}
}
