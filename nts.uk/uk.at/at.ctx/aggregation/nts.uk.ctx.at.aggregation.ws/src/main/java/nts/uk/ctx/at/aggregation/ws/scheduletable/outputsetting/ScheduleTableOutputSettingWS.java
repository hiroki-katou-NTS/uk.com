package nts.uk.ctx.at.aggregation.ws.scheduletable.outputsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting.ScheduleTableOutputSettingDto;
import nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting.ScheduleTableOutputSettingFinder;

/**
 * 
 * @author quytb
 *
 */
@Path("ctx/at/schedule/scheduletable")
@Produces("application/json")
public class ScheduleTableOutputSettingWS extends WebService{
	
	@Inject
	private ScheduleTableOutputSettingFinder finder;
	
	@POST
	@Path("getone/{code}")
	public ScheduleTableOutputSettingDto getScheduleTableOutputSettingByCidAndCode(@PathParam("code") String code){
		return this.finder.findByCidAndCode(code);
	}
	
	@POST
	@Path("getall")
	public List<ScheduleTableOutputSettingDto> getScheduleTableOutputSettingByCid(){
		return this.finder.findByCid();
	}
}
