package nts.uk.screen.at.ws.kmk.kmk004.d;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.d.BasicSettingsByEmployment;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentCodeDto;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004DWebSevice extends WebService {

	@Inject
	private EmploymentList employmentList;
	
	@Inject
	private BasicSettingsByEmployment baseSetting;
	
	
	@POST
	@Path("viewd/emp/getEmploymentId")
	public List<EmploymentCodeDto> getEmploymentCode() {
		return this.employmentList.get(LaborWorkTypeAttr.DEFOR_LABOR);
	}
	
	@POST
	@Path("viewd/emp/getBaseSetting/{employmentCode}")
	public DisplayBasicSettingsDto getBaseSetting(@PathParam("employmentCode") String employmentCode) {
		return this.baseSetting.getSetting(employmentCode);
	}
}
