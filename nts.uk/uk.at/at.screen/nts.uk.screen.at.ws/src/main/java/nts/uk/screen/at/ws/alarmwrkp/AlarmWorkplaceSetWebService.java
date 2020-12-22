package nts.uk.screen.at.ws.alarmwrkp;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.RangeCompareType;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.SingleValueCompareType;
import nts.uk.screen.at.app.alarmwrkp.AlarmCheckCategoryList;
import nts.uk.screen.at.app.alarmwrkp.AlarmPatternSetDto;
import nts.uk.screen.at.app.alarmwrkp.AlarmPatternSetWorkPlaceQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
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

	@POST
	@Path("getEnumCompareType")
	public List<EnumConstant> getEnumSingleValueCompareType(){
		val result = new ArrayList<EnumConstant>();
		result.addAll(EnumAdaptor.convertToValueNameList(SingleValueCompareType.class));
		result.addAll(EnumAdaptor.convertToValueNameList(RangeCompareType.class));
		return result;
	}

}
