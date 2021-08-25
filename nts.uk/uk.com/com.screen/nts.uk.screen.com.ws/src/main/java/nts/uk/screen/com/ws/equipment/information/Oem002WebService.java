package nts.uk.screen.com.ws.equipment.information;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.find.equipment.information.EquipmentInformationDto;
import nts.uk.screen.com.app.find.equipment.information.EquipmentInformationScreenQuery;
import nts.uk.screen.com.app.find.equipment.information.EquipmentInformationStartupDto;

@Path("com/screen/oem002/")
@Produces("application/json")
public class Oem002WebService extends WebService {

	@Inject
	private EquipmentInformationScreenQuery screenQuery;
	
	@POST
	@Path("initScreen")
	public EquipmentInformationStartupDto initScreen() {
		return this.screenQuery.getStartupList();
	}
	
	@POST
	@Path("getEquipmentInfo")
	public EquipmentInformationDto getEquipmentInfo(String code) {
		return this.screenQuery.getEquipmentInfo(code);
	}
}
