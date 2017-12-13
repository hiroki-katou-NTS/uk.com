/**
 * 
 */
package nts.uk.ctx.pereg.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.layout.MaintenanceLayoutCommand;
import nts.uk.ctx.pereg.app.command.layout.MaintenanceLayoutCommandHandler;
import nts.uk.ctx.pereg.app.find.layout.LayoutFinder;
import nts.uk.ctx.pereg.app.find.layout.LayoutQuery;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layout.dto.SimpleEmpMainLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.MaintenanceLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.MaintenanceLayoutFinder;

@Path("ctx/pereg/person/maintenance")
@Produces("application/json")
public class MaintenanceLayoutWebservices extends WebService {

	@Inject
	private MaintenanceLayoutFinder mlayoutFinder;

	@Inject
	private MaintenanceLayoutCommandHandler commandHandler;

	@Inject
	private LayoutFinder layoutFinder;

	@POST
	@Path("findAll")
	public List<MaintenanceLayoutDto> getAllMaintenenceLayout() {
		return mlayoutFinder.getAllLayout();
	}

	@POST
	@Path("findOne/{lid}")
	public MaintenanceLayoutDto getLayoutById(@PathParam("lid") String lid) {
		MaintenanceLayoutDto x = mlayoutFinder.getDetails(lid);
		return x;

	}

	@POST
	@Path("saveLayout")
	public void addMaintenanceLayout(MaintenanceLayoutCommand command) {
		this.commandHandler.handle(command);
	}

	@POST
	@Path("findSimple/{empId}")
	public List<SimpleEmpMainLayoutDto> getSimpleLayoutList(@PathParam("empId") String browsingEmpId) {
		return layoutFinder.getSimpleLayoutList(browsingEmpId);
	}
	
	@POST
	@Path("findLayoutData")
	public EmpMaintLayoutDto getLayoutData(LayoutQuery layoutQuery) {
		return layoutFinder.getLayout(layoutQuery);
	}

}
