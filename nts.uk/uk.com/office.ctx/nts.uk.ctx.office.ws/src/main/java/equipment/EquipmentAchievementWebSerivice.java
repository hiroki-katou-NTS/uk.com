package equipment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.office.app.command.equipment.achievement.InsertEquipmentUsageSettings;
import nts.uk.ctx.office.app.command.equipment.achievement.InsertEquipmentUsageSettingsCommand;

@Path("ctx/office/equipment/achivement/")
@Produces("application/json")
public class EquipmentAchievementWebSerivice {

	@Inject
	private InsertEquipmentUsageSettings insertEquipmentUsageSettings;
	
	@POST
	@Path("register")
	public void register(InsertEquipmentUsageSettingsCommand command) {
		insertEquipmentUsageSettings.handle(command);
	}
}
