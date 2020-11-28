package nts.uk.screen.at.ws.alarmwrkp;

import nts.uk.screen.at.app.alarmwrkp.AlarmCheckCategoryList;
import nts.uk.screen.at.app.alarmwrkp.AlarmPatternSetDto;
import nts.uk.screen.at.app.alarmwrkp.AlarmPatternSetWorkPlaceQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/record/alarmwrkp/screen")
@Produces("application/json")
public class AlarmWorkplaceSetWebService {
	
	@Inject
	private AlarmPatternSetWorkPlaceQuery alarmPatternSetWorkPlaceQuery;
	
	@POST
	@Path("getAll")
	public List<AlarmPatternSetDto> getAllAlarmSet() {
		return alarmPatternSetWorkPlaceQuery.getAll();
	}

	@POST
	@Path("getAllCtg")
	public List<AlarmCheckCategoryList> getAllCtgItem() {
		return alarmPatternSetWorkPlaceQuery.getAllCtgItem();
	}
	
}
