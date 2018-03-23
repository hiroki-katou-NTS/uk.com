package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumCommand;
import nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantFinder;

@Path("at/record/remainnumber/special")
@Produces("application/json")
public class SpecialLeaveGrantRemWebService {
	
	@Inject
	SpecialLeaveGrantFinder finder;	
	


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
	public void add(AnnLeaGrantRemnNumCommand command){
		// todo
	}
	
	@POST
	@Path("update")
	public void update(AnnLeaGrantRemnNumCommand command){
		//todo
	}
	
	@POST
	@Path("delete")
	public void remove(AnnLeaGrantRemnNumCommand command){
		//todo
	}
}
