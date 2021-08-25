package nts.uk.screen.com.ws.equipment.information;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.query.equipment.classificationmaster.EquipmentClassificationDto;
import nts.uk.ctx.office.app.query.equipment.classificationmaster.EquipmentClassificationListQuery;
import nts.uk.screen.com.app.find.equipment.information.EquipmentInformationDto;
import nts.uk.screen.com.app.find.equipment.information.EquipmentInformationScreenQuery;
import nts.uk.screen.com.app.find.equipment.information.EquipmentInformationStartupDto;
import nts.uk.shr.com.context.AppContexts;

@Path("com/screen/oem002/")
@Produces("application/json")
public class Oem002WebService extends WebService {

	@Inject
	private EquipmentInformationScreenQuery equipmentInformationScreenQuery;
	
	@Inject
	private EquipmentClassificationListQuery equipmentClassificationListQuery;
	
	@POST
	@Path("initScreen")
	public EquipmentInformationStartupDto initScreen() {
		return this.equipmentInformationScreenQuery.getStartupList();
	}
	
	@POST
	@Path("getEquipmentInfo/{code}")
	public EquipmentInformationDto getEquipmentInfo(@PathParam("code") String code) {
		return this.equipmentInformationScreenQuery.getEquipmentInfo(code);
	}
	
	@POST
	@Path("getEquipmentClsList")
	public List<EquipmentClassificationDto> getEquipmentClsList() {
		return this.equipmentClassificationListQuery.get(AppContexts.user().contractCode());
	}
}
