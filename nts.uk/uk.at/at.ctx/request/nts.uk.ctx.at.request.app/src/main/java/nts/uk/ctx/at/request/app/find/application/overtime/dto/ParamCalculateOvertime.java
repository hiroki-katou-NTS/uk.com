package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

public class ParamCalculateOvertime {
	
	public String employeeID; 
	public String appDate; 
	public Integer prePostAtr; 
	public String workTypeCD; 
	public String workTimeCD;
	public List<CaculationTime> overtimeInputLst;
	public Integer startTime;
	public Integer endTime;
	public List<Integer> startTimeRests; 
	public List<Integer> endTimeRests;
	public Optional<Application_New> opAppBefore;
	public boolean beforeAppStatus;
	public int actualStatus;
	public List<OvertimeColorCheck> actualLst;
	
	public List<Integer> getStartTimeRests() {

		List<Integer> result = new ArrayList<>();
		startTimeRests.forEach(x -> {
			if (x != null) {
				result.add(x);
			}
		});

		return result;
	}
	
	public List<Integer> getEndTimeRests() {

		List<Integer> result = new ArrayList<>();
		endTimeRests.forEach(x -> {
			if (x != null) {
				result.add(x);
			}
		});

		return result;
	}
	
}
