package nts.uk.ctx.bs.employee.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import find.layout.NewLayoutDto;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.layout.GetLayoutByCeateTypeDto;
import nts.uk.ctx.bs.employee.app.find.layout.LayoutFinder;
import nts.uk.ctx.bs.employee.app.find.layout.LayoutQuery;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;

@Path("bs/employee/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutWebService extends WebService {

	@Inject
	private LayoutFinder layoutFinder;
	
	@Path("getByEmp")
	@POST
	public EmpMaintLayoutDto getByCreateType(LayoutQuery query) {
		return this.layoutFinder.getLayout(query);
	}

	@Path("getByCreateType")
	@POST
	public NewLayoutDto getByCreateType(GetLayoutByCeateTypeDto command) {
		return this.layoutFinder.getByCreateType(command);
	}

}
