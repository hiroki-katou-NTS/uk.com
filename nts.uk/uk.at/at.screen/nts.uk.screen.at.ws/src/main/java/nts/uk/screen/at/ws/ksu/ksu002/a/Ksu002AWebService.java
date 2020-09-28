package nts.uk.screen.at.ws.ksu.ksu002.a;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.ksu.ksu002.a.GetScheduleActualOfWorkInfo002;
import nts.uk.screen.at.app.query.ksu.ksu002.a.GetScheduleOfWorkInfo002;
import nts.uk.screen.at.app.query.ksu.ksu002.a.GetWorkActualOfWorkInfo002;
import nts.uk.screen.at.app.query.ksu.ksu002.a.TheInitialDisplayDate;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.TheInitialDisplayDateDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;

@Path("screen/ksu/ksu002/")
@Produces("application/json")
public class Ksu002AWebService extends WebService {

	// 初期表示の年月を取得する
	@Inject
	private TheInitialDisplayDate theInitialDisplayDate;
	
	// 勤務予定（勤務情報）を取得する
	@Inject
	private GetScheduleOfWorkInfo002 getScheduleOfWorkInfo;
	
	// 勤務実績（勤務情報）を取得する
	@Inject
	private GetWorkActualOfWorkInfo002 getWorkActualOfWorkInfo;
	
	// 予定・実績を勤務情報で取得する
	@Inject
	private GetScheduleActualOfWorkInfo002 getScheduleActualOfWorkInfo;
	

	@POST
	@Path("getInitialDate")
	public TheInitialDisplayDateDto get() {
		return this.theInitialDisplayDate.getInitialDisplayDate();
	}
	
	@POST
	@Path("getScheduleOfWorkInfo")
	public List<WorkScheduleWorkInforDto> getScheduleOfWorkInfo(DisplayInWorkInfoInput param) {
		return this.getScheduleOfWorkInfo.getDataScheduleOfWorkInfo(param);
	}
	
	@POST
	@Path("getWorkActualOfWorkInfo")
	public List<WorkScheduleWorkInforDto> getWorkActualOfWorkInfo(DisplayInWorkInfoInput param) {
		return this.getWorkActualOfWorkInfo.getDataActualOfWorkInfo(param);
	}
	
	@POST
	@Path("getScheduleActualOfWorkInfo")
	public List<WorkScheduleWorkInforDto> getScheduleActualOfWorkInfo(DisplayInWorkInfoInput param) {
		return this.getScheduleActualOfWorkInfo.getDataScheduleAndAactualOfWorkInfo(param);
	}
}
