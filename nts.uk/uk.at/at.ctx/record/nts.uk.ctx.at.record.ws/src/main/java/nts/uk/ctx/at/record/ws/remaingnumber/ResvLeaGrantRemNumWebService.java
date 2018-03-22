package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.AddResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum.UpdateResvLeaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumDto;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Path("record/remainnumber/resv-lea")
@Produces("application/json")
public class ResvLeaGrantRemNumWebService extends WebService{

	
	//@Inject
	//private AnnLeaGrantRemnNumFinder finder;
	
	@Inject
	private AddResvLeaCommandHandler addHandler;
	
	@Inject
	private UpdateResvLeaCommandHandler updateHandler;
	
	@POST
	@Path("get-resv-lea/{empId}")
	public List<ResvLeaGrantRemNumDto> getAllOK(@PathParam("empId") String employeeId) {
		return getData();
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
	
	private List<ResvLeaGrantRemNumDto> getData(){
		List<ResvLeaGrantRemNumDto> lst = new ArrayList<>();
		for(int i = 1; i < 20; i++) {
			lst.add(new ResvLeaGrantRemNumDto(GeneralDate.fromString("2018/10/" + i, "YYYY/MM/DD"), GeneralDate.fromString("2018/05/" + i, "YYYY/MM/DD"), 
					EnumAdaptor.valueOf(i%2==0?1: 0, LeaveExpirationStatus.class), Double.parseDouble(i + ""), Double.parseDouble(i + ""), Double.parseDouble(i + ""), Double.parseDouble(i + "")));
		}
		return lst;
	}
	
}
