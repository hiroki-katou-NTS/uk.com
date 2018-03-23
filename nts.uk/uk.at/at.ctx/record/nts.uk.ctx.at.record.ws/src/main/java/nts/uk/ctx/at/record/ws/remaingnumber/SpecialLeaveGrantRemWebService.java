package nts.uk.ctx.at.record.ws.remaingnumber;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.AddSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.AddSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.DeleteSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.DeleteSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.UpdateSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.UpdateSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant.SpecialLeaveGrantFinder;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

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
	@Path("getall")
	public List<SpecialLeaveGrantDto> getAll() {
		List<SpecialLeaveGrantDto> datatest = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SpecialLeaveGrantDto dto = SpecialLeaveGrantDto.createFromDomain(SpecialLeaveGrantRemainingData.createFromJavaType("", "", i, 
					GeneralDate.legacyDate(Date.valueOf("2018/03/23")),
					GeneralDate.legacyDate(Date.valueOf("2018/03/23")), 0, 0, 10, 12, 14.0, 15, 16.0, 17, 18, 19.0, 20));
			datatest.add(dto);
		}
		
		//return finder.getListData(sid, ctgcode);
		// @PathParam("sid") String sid ,@PathParam("ctgcd") int ctgcode
		return datatest;
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
