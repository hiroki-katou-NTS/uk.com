package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.AddResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.UpdateResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumDto;

@Path("at/record/remainnumber/resvLea")
@Produces("application/json")
public class ResvLeaGrantRemNumWebService extends WebService{

	
	//@Inject
	//private AnnLeaGrantRemnNumFinder finder;
	
	@Inject
	private AddResvLeaCommandHandler addHandler;
	
	@Inject
	private UpdateResvLeaCommandHandler updateHandler;
	
	@POST
	@Path("getResvLea/{empId}")
	public List<AnnLeaGrantRemnNumDto> findResvLeaGrantRemnNum(@PathParam("empId") String employeeId) {
		return null;
	}
	
	@POST
	@Path("add")
	public void add(ResvLeaGrantRemNumCommand command) {
		addHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(ResvLeaGrantRemNumCommand command) {
		updateHandler.handle(command);
	}
	
}
