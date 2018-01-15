package nts.uk.ctx.at.record.ws.calculationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.calculationsetting.StampReflectionManagementCommand;
import nts.uk.ctx.at.record.app.command.calculationsetting.StampReflectionManagementCommandHandler;
import nts.uk.ctx.at.record.app.find.calculationsetting.StampReflectionManagementDto;
import nts.uk.ctx.at.record.app.find.calculationsetting.StampReflectionManagementFinder;

@Path("at/record/calculation")
@Produces("application/json")
public class StampReflectionManagementWebService {
	@Inject
	private StampReflectionManagementFinder finder;
	@Inject
	private StampReflectionManagementCommandHandler handler;
	
	@POST
	@Path("findByCode")
	public StampReflectionManagementDto getStampReflection(){
		return this.finder.findByCode();
	}
	
	@Path("add")
	@POST
	public void add(StampReflectionManagementCommand command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(StampReflectionManagementCommand command) {
		this.handler.handle(command);
	}
}
