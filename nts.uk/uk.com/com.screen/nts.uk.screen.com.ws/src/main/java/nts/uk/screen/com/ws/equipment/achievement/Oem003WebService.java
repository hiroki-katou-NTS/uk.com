package nts.uk.screen.com.ws.equipment.achievement;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.find.equipment.achievement.EquipmentUsageSettingsDto;
import nts.uk.screen.com.app.find.equipment.achievement.EquipmentUsageSettingsScreenQuery;

@Path("com/screen/oem003/")
@Produces("application/json")
public class Oem003WebService extends WebService {

	@Inject
	private EquipmentUsageSettingsScreenQuery screenQuery;
	
	@POST
	@Path("findSettings")
	public EquipmentUsageSettingsDto findSettings() {
		return this.screenQuery.findSettings();
	}
}
