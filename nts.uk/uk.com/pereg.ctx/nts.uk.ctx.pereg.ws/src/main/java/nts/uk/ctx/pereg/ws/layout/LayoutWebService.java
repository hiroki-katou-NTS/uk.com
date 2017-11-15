package nts.uk.ctx.pereg.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import find.layout.NewLayoutDto;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.layout.GetLayoutByCeateTypeDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;

/**
 * @author sonnlb
 *
 */
@Path("reginfo/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutWebService extends WebService {

	@Inject
	private RegisterLayoutFinder layoutFinder;

	@Path("getByCreateType")
	@POST
	public NewLayoutDto getByCreateType(GetLayoutByCeateTypeDto command) {
		return this.layoutFinder.getByCreateType(command);
	}

}
