package nts.uk.ctx.at.shared.ws.calculation.setting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.calculation.setting.AddDeformLaborOTCommand;
import nts.uk.ctx.at.shared.app.command.calculation.setting.AddDeformLaborOTCommandHandler;
import nts.uk.ctx.at.shared.app.find.calculation.setting.DeformLaborOTDto;
import nts.uk.ctx.at.shared.app.find.calculation.setting.DeformLaborOTFinder;

/**
 * @author yennh
 *
 */
@Path("shared/calculation/setting")
@Produces("application/json")
public class DeformLaborOTWebService extends WebService {
	@Inject
	private DeformLaborOTFinder finder;
	@Inject
	private AddDeformLaborOTCommandHandler handler;

	@Path("find")
	@POST
	public List<DeformLaborOTDto> findByCid() {
		return finder.findAllDeformLaborOT();
	}
	
	@Path("add")
	@POST
	public void add(AddDeformLaborOTCommand command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(AddDeformLaborOTCommand command) {
		this.handler.handle(command);
	}
}
