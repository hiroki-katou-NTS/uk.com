package nts.uk.ctx.at.shared.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.AddResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.AddResvLeaRemainCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.RemoveResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.RemoveResvLeaRemainCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.UpdateResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.UpdateResvLeaRemainCommand;
import nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumFinder;

@Path("record/remainnumber/resv-lea")
@Produces("application/json")
public class ResvLeaGrantRemNumWebService extends WebService{

	
	@Inject
	private ResvLeaGrantRemNumFinder finder;
	
	@Inject
	private AddResvLeaCommandHandler addHandler;
	
	@Inject
	private UpdateResvLeaCommandHandler updateHandler;
	
	@Inject
	private RemoveResvLeaCommandHandler removeHandler;
	
	@POST
	@Path("get-resv-lea/{empId}/{isall}")
	public List<ResvLeaGrantRemNumDto> getAll(@PathParam("empId") String empId, @PathParam("isall") boolean isAll){
		if(!isAll){
			List<ResvLeaGrantRemNumDto> lst =   finder.findNotExp(empId);
			return lst;
		}else{
			List<ResvLeaGrantRemNumDto> lst = finder.find(empId);
			return lst;
		}
		
	}
	@POST
	@Path("get-resv-lea-by-id/{itemId}")
	public ResvLeaGrantRemNumDto getById(@PathParam("itemId")String id){
		return finder.getById(id);
	}
	
	@POST
	@Path("generate-deadline")
	public GeneralDate getById(GeneralDate grantDate){
		return finder.generateDeadline(grantDate);
	}
	
	@POST
	@Path("add")
	public List<ResvLeaGrantRemNumDto> add(AddResvLeaRemainCommand command) {
		return addHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateResvLeaRemainCommand command) {
		updateHandler.handle(command);
	}
	
	@POST
	@Path("remove")
	public void remove(RemoveResvLeaRemainCommand command) {
		removeHandler.handle(command);
	}
}
