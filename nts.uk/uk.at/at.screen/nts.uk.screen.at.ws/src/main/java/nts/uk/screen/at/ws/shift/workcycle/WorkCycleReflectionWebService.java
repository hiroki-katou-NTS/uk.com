package nts.uk.screen.at.ws.shift.workcycle;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.ReflectionImage;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCycleRefSetting;
import nts.uk.screen.at.app.shift.workcycle.BootMode;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDialog;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDto;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 勤務サイクル反映ダイアログ Webservice
 * @author khai.dh
 */
@Path("screen/at/shift/workcycle/workcycle-reflection")
@Produces(MediaType.APPLICATION_JSON)
public class WorkCycleReflectionWebService extends WebService {
	private final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";

	@Inject private WorkCycleReflectionDialog wcrdScreenQuery;

    @POST
    @Path("start")
    public WorkCycleReflectionDto getStartupInfo(
			@FormParam("bootMode") BootMode bootMode,
			@FormParam("creationPeriodStartDate") String creationPeriodStartDate,
			@FormParam("creationPeriodEndDate") String creationPeriodEndDate,
			@FormParam("workCycleCode") String workCycleCode,
			@FormParam("refOrder") List<WorkCreateMethod> refOrder,
			@FormParam("numOfSlideDays") int numOfSlideDays){
		DatePeriod creationPeriod = createDatePeriod(creationPeriodStartDate, creationPeriodEndDate, DATE_FORMAT_YYYYMMDD);

		return wcrdScreenQuery.getStartupInfo(
        		bootMode,
				creationPeriod,
				workCycleCode,
				refOrder,
				numOfSlideDays);
    }

	/**
	 * 勤務サイクルの適用イメージを取得する
	 * @param creationPeriodStartDate	作成期間 start
	 * @param creationPeriodEndDate		作成期間 end
	 * @param workCycleCode				勤務サイクルコード
	 * @param refOrder					反映順序
	 * @param numOfSlideDays			スライド日数
	 * @param legalHolidayCd			祝日の勤務種類
	 * @param nonStatutoryHolidayCd		法定外休日の勤務種類
	 * @param holidayCd					法定休日の勤務種類
	 * @return 反映イメージ
	 */
	@POST
	@Path("get-reflection-image")
	public ReflectionImage getWorkCycleAppImage(
			@FormParam("creationPeriodStartDate") String creationPeriodStartDate,
			@FormParam("creationPeriodEndDate") String creationPeriodEndDate,
			@FormParam("workCycleCode") String workCycleCode,
			@FormParam("refOrder") List<WorkCreateMethod> refOrder,
			@FormParam("numOfSlideDays") int numOfSlideDays,
			@FormParam("legalHolidayCd") String legalHolidayCd,
			@FormParam("nonStatutoryHolidayCd") String nonStatutoryHolidayCd,
			@FormParam("holidayCd") String holidayCd){

		WorkCycleRefSetting config = new WorkCycleRefSetting(
				workCycleCode,
				refOrder,
				numOfSlideDays,
				legalHolidayCd,
				nonStatutoryHolidayCd,
				holidayCd
		);
		DatePeriod creationPeriod = createDatePeriod(creationPeriodStartDate, creationPeriodEndDate, DATE_FORMAT_YYYYMMDD);

		return wcrdScreenQuery.getWorkCycleAppImage(creationPeriod, config);
	}

	private DatePeriod createDatePeriod(String startDate, String endDate, String format){
		return new DatePeriod(GeneralDate.fromString(startDate,format), GeneralDate.fromString(endDate,format));
	}
}
