package nts.uk.ctx.at.request.ws.application.overtime;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;

public class OvertimeCalcParam {
	public String appDate;
	public String employeeID; 
	public Integer prePostAtr; 
	public String workTypeCD;
	public String siftCD;
	public Integer startTime; 
	public Integer endTime;
	public List<OvertimeColorCheck> overTimeLst; 
	public List<OvertimeColorCheck> breakTimeLst; 
	public Optional<Application_New> opAppBefore; 
	public boolean beforeAppStatus;
	public List<OvertimeColorCheck> actualLst; 
	public Integer actualStatus;
	public Integer preExcessDisplaySetting; 
	public Integer performanceExcessAtr;
}
