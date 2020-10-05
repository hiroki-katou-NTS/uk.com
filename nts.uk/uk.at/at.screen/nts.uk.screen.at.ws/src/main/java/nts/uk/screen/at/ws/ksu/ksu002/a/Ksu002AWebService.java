package nts.uk.screen.at.ws.ksu.ksu002.a;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.screen.at.app.query.ksu.ksu002.a.GetDateInfoDuringThePeriod;
import nts.uk.screen.at.app.query.ksu.ksu002.a.GetScheduleActualOfWorkInfo002;
import nts.uk.screen.at.app.query.ksu.ksu002.a.GetWorkTypeKSU002;
import nts.uk.screen.at.app.query.ksu.ksu002.a.ListOfPeriodsClose;
import nts.uk.screen.at.app.query.ksu.ksu002.a.TheInitialDisplayDate;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.SystemDateDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkTypeDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.GetDateInfoDuringThePeriodInput;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.ListOfPeriodsCloseInput;

@Path("screen/ksu/ksu002/")
@Produces("application/json")
public class Ksu002AWebService extends WebService {

	// 初期表示の年月を取得する
	@Inject
	private TheInitialDisplayDate theInitialDisplayDate;
	
	// 締めに応じる期間リストを取得する
	@Inject
	private ListOfPeriodsClose listOfPeriodsClose;

	@Inject
	private GetScheduleActualOfWorkInfo002 getScheduleActualOfWorkInfo002;
	
	@Inject
	private GetWorkTypeKSU002 getWorkType;
	
	@Inject
	private GetDateInfoDuringThePeriod getDateInfoDuringThePeriod;
	
	@POST
	@Path("getListOfPeriodsClose")
	public SystemDateDto getListOfPeriodsClose(ListOfPeriodsCloseInput param) {
		if (param == null) {
			param = new ListOfPeriodsCloseInput(theInitialDisplayDate.getInitialDisplayDate().getYearMonth());
		}
		
		return this.listOfPeriodsClose.get(param);
	}
	
	@POST
	@Path("displayInWorkInformation")
	public List<WorkScheduleWorkInforDto> getScheduleActualOfWorkInfo(DisplayInWorkInfoInput param) {		
		return this.getScheduleActualOfWorkInfo002.getDataScheduleAndAactualOfWorkInfo(param);
	}
	
	@POST
	@Path("getWorkType")
	public List<WorkTypeDto> getWorkType() {
		return this.getWorkType.getWorkType();
	}
	
	@POST
	@Path("getDateInfo")
	public List<DateInformation> getWorkType(GetDateInfoDuringThePeriodInput param) {
		return this.getDateInfoDuringThePeriod.get(param);
	}
}
