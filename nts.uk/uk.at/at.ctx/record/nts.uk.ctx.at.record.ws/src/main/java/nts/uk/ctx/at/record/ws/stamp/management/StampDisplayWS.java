package nts.uk.ctx.at.record.ws.stamp.management;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.stamp.management.AddStampPageLayoutCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.AddStampSettingPersonCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.AddStampSettingPersonCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.UpdateStampPageLayoutCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.management.UpdateStampSettingPersonCommandHandler;
import nts.uk.ctx.at.record.app.find.stamp.management.StamDisplayFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.StampSettingPersonDto;

@Path("at/record/stamp")
@Produces("application/json")
public class StampDisplayWS extends WebService {
	
	@Inject
	private StamDisplayFinder finder; 
	
	@Inject
	private AddStampSettingPersonCommandHandler addSetPerHandler;
	
	@Inject
	private UpdateStampSettingPersonCommandHandler updateSetPerHandler;
	
	@Inject
	private AddStampPageLayoutCommandHandler  addStampPageHandler;
	
	@Inject
	private UpdateStampPageLayoutCommandHandler  updateStampPageHandler;
	
	@POST
	@Path("getStampSetting")
	public Optional<StampSettingPersonDto> getStampNumber() {
		return this.finder.getStampSet();
	}

	@POST
	@Path("saveStampSetting")
	public void save(AddStampSettingPersonCommand command) {
		this.addSetPerHandler.handle(command);
	}
	
	@POST
	@Path("updateStampSetting")
	public void update(AddStampSettingPersonCommand command) {
		this.updateSetPerHandler.handle(command);
	}
	
	@POST
	@Path("saveStampPage")
	public void save(StampPageLayoutCommand command) {
		this.addStampPageHandler.handle(command);
	}
	
	@POST
	@Path("updateStampPage")
	public void update(StampPageLayoutCommand command) {
		this.updateStampPageHandler.handle(command);
	}
}
