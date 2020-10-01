package nts.uk.screen.at.ws.ksu.ksu002.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.ksu.ksu002.a.DisplayInWorkInformation002;
import nts.uk.screen.at.app.query.ksu.ksu002.a.ListOfPeriodsClose;
import nts.uk.screen.at.app.query.ksu.ksu002.a.TheInitialDisplayDate;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.DisplayInWorkInfoDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.SystemDateDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.TheInitialDisplayDateDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;
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

	// 予定・実績を勤務情報で取得する
	@Inject
	private DisplayInWorkInformation002 displayInWorkInformation002;
	

	@POST
	@Path("getInitialDate")
	public TheInitialDisplayDateDto get() {
		return this.theInitialDisplayDate.getInitialDisplayDate();
	}
	
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
	public DisplayInWorkInfoDto getScheduleActualOfWorkInfo(DisplayInWorkInfoInput param) {		
		return this.displayInWorkInformation002.getDataWorkInfo(param);
	}
}
