package nts.uk.ctx.office.ws.equipment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.command.equipment.EquipmentInformationCommand;
import nts.uk.ctx.office.app.command.equipment.EquipmentInformationDeleteCommandHandler;
import nts.uk.ctx.office.app.command.equipment.EquipmentInformationInsertCommandHandler;
import nts.uk.ctx.office.app.command.equipment.EquipmentInformationUpdateCommandHandler;
import nts.uk.ctx.office.app.find.equipment.EquipmentInformationScreenQuery;
import nts.uk.ctx.office.app.find.equipment.EquipmentInformationStartupDto;

@Path("ctx/office/equipment/information/")
@Produces("application/json")
public class EquipmentInformationWebService extends WebService {

	@Inject
	private EquipmentInformationInsertCommandHandler insertCommandHandler;
	
	@Inject
	private EquipmentInformationUpdateCommandHandler updateCommandHandler;
	
	@Inject
	private EquipmentInformationDeleteCommandHandler deleteCommandHandler;
	
	@Inject
	private EquipmentInformationScreenQuery screenQuery;
	
	@POST
	@Path("insert")
	public void insert(EquipmentInformationCommand command) {
		this.insertCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(EquipmentInformationCommand command) {
		this.updateCommandHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(EquipmentInformationCommand command) {
		this.deleteCommandHandler.handle(command);
	}
	
	@POST
	@Path("initScreen")
	public EquipmentInformationStartupDto initScreen() {
		return this.screenQuery.getStartupList();
	}
}
