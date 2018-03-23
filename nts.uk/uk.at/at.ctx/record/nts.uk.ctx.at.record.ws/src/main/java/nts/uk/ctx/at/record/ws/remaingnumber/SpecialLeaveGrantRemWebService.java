package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.AddSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.AddSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.DeleteSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.DeleteSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.UpdateSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.UpdateSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantFinder;

@Path("at/record/remainnumber/special")
@Produces("application/json")
public class SpecialLeaveGrantRemWebService {
	
	@Inject
	SpecialLeaveGrantFinder finder;	
	
	@Inject
	AddSpecialLeaCommandHandler add;
	
	@Inject
	UpdateSpecialLeaCommandHandler update;
	
	@Inject
	DeleteSpecialLeaCommandHandler delete;


	@POST
	@Path("getall/{sid}/{ctgcode}")
	public List<SpecialLeaveGrantDto> getAll(@PathParam("sid") String sid ,@PathParam("ctgcd") int ctgcode) {
		return finder.getListData(sid, ctgcode);
	}
	
	@POST
	@Path("getAnnLeaByCheckState")
	public List<SpecialLeaveGrantDto> getAnnLeaByCheckState(String employeeId,int ctgcode, Boolean checkState) {
		return finder.getListDataByCheckState(employeeId,ctgcode,checkState);
	}
	
	
	@POST
	@Path("add")
	public void add(AddSpecialLeaCommand command){
		add.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateSpecialLeaCommand command){
		update.handle(command);
	}
	
	@POST
	@Path("delete")
	public void remove(DeleteSpecialLeaCommand command){
		delete.handle(command);
	}
}
