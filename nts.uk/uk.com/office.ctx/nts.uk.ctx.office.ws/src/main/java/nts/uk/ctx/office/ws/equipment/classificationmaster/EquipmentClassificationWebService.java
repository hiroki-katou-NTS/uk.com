package nts.uk.ctx.office.ws.equipment.classificationmaster;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.command.equipment.classificationmaster.CreateEquipmentClsCommandHandler;
import nts.uk.ctx.office.app.command.equipment.classificationmaster.EquipmentClsCommand;
import nts.uk.ctx.office.app.command.equipment.classificationmaster.UpdateEquipmentClsCommandHandler;
import nts.uk.ctx.office.app.query.equipment.classificationmaster.EquipmentClassificationDto;
import nts.uk.ctx.office.app.query.equipment.classificationmaster.EquipmentClassificationListQuery;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/office/equipment/classificationmaster/")
@Produces("application/json")
public class EquipmentClassificationWebService extends WebService {

	@Inject
	private EquipmentClassificationListQuery query;
	
	@Inject
	private CreateEquipmentClsCommandHandler createCommandHandler;
	
	@Inject
	private UpdateEquipmentClsCommandHandler updateCommandHandler;
	
	@POST
	@Path("insert")
	public void insert(EquipmentClsCommand command) {
		this.createCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(EquipmentClsCommand command) {
		this.updateCommandHandler.handle(command);
	}
	
	@POST
	@Path("getAll")
	public List<EquipmentClassificationDto> getAll() {
		return this.query.get(AppContexts.user().contractCode());
	}
	
}
