package nts.uk.ctx.at.shared.ws.remaingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.AddSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.DeleteSpecialLeaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.DeleteSpecialLeaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant.SpecialLeaveRemainCommand;
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
	@Path("getall/{sid}/{spicialCode}")
	public List<SpecialLeaveGrantDto> getAll( @PathParam("sid") String sid ,@PathParam("spicialCode") int spicialCode) {
		List<SpecialLeaveGrantDto> datatest = new ArrayList<>();
		datatest =  finder.getListData(sid, spicialCode);
		return datatest;
	}

	@POST
	@Path("get-detail/{specialid}")
	public SpecialLeaveGrantDto getDetail( @PathParam("specialid") String specialid) {
		Optional<SpecialLeaveGrantRemainingData> data= finder.getDetail(specialid);
		if (data.isPresent()) {
			return SpecialLeaveGrantDto.createFromDomain(data.get());
		}
		return null;
		

	}

	@POST
	@Path("save")
	public void save(SpecialLeaveRemainCommand command) {
		if (command.getSpecialid() == null) {
			add.handle(command);
		} else {
			update.handle(command);
		}
	}

	@POST
	@Path("delete")
	public void remove(DeleteSpecialLeaCommand command) {
		System.out.println(command);
		delete.handle(command);
	}
}
