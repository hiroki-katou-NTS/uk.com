package nts.uk.screen.at.ws.shift.workcycle;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCycleRefSetting;
import nts.uk.screen.at.app.ksm003.find.WorkCycleDto;
import nts.uk.screen.at.app.shift.workcycle.BootMode;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDialog;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * 勤務サイクル反映ダイアログ Webservice
 * @author khai.dh
 */
@Path("screen/at/shift/workcycle/workcycle-reflection")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class WorkCycleReflectionWebService extends WebService {
	private final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-d";

	@Inject private WorkCycleReflectionDialog wcrdScreenQuery;

    @POST
    @Path("start")
	@Consumes(MediaType.APPLICATION_JSON)
    public WorkCycleReflectionDto getStartupInfo(GetStartupInfoParam param) {

		DatePeriod creationPeriod = createDatePeriod(
			param.getCreationPeriodStartDate(),
			param.getCreationPeriodEndDate(),
			DATE_FORMAT_YYYYMMDD);

		List<WorkCreateMethod> refOrder = createFromIntArray(param.getRefOrder());

		int bootModeParam = param.getBootMode();
		BootMode bootMode = (bootModeParam == 0)? BootMode.REF_MODE:BootMode.EXEC_MODE;

		return wcrdScreenQuery.getStartupInfo(
				bootMode,
				creationPeriod,
				param.getWorkCycleCode(),
				refOrder,
				param.getNumOfSlideDays());
    }

	/**
	 * 勤務サイクルの適用イメージを取得する
	 * @return 反映イメージ
	 */
	@POST
	@Path("get-reflection-image")
	public List<WorkCycleReflectionDto.RefImageEachDayDto> getWorkCycleAppImage(GetWorkCycleAppImageParam param){

		List<WorkCreateMethod> refOrder = createFromIntArray(param.getRefOrder());
		WorkCycleRefSetting config = new WorkCycleRefSetting(
				param.getWorkCycleCode(),
				refOrder,
				param.getNumOfSlideDays(),
				param.getLegalHolidayCd(),
				param.getNonStatutoryHolidayCd(),
				param.getHolidayCd()
		);

		DatePeriod creationPeriod = createDatePeriod(
				param.getCreationPeriodStartDate(),
				param.getCreationPeriodEndDate(),
				DATE_FORMAT_YYYYMMDD);

		return wcrdScreenQuery.getWorkCycleAppImage(creationPeriod, config);
	}

	private DatePeriod createDatePeriod(String startDate, String endDate, String format){
		return new DatePeriod(GeneralDate.fromString(startDate,format), GeneralDate.fromString(endDate,format));
	}

	private List<WorkCreateMethod> createFromIntArray(int[] input){
		List<WorkCreateMethod> workCreateMethods = new ArrayList<>();
		for (int i:input) {
			workCreateMethods.add(EnumAdaptor.valueOf(i, WorkCreateMethod.class));
		}

		return workCreateMethods;
	}

}
