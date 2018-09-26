package nts.uk.ctx.pereg.ws.facade;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.AnnLeaEmpBasicInfo;
import nts.uk.ctx.pereg.app.find.common.GetYearHolidayInfo;
import nts.uk.ctx.pereg.app.find.common.NextTimeEventDto;
import nts.uk.ctx.pereg.app.find.common.NextTimeEventParam;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Path("at/record/remainnumber/annlea/event")
@Produces("application/json")
public class NextTimeEventService extends WebService{
	
	@Inject 
	private GetYearHolidayInfo getYearHolidayInfo;
	
	/**
	 * Category: CS00024
	 * アルゴリズム「次回年休情報を取得する」
　	 * パラメータ＝社員ID：画面で選択している社員ID
　	 * パラメータ＝年休付与基準日：IS00279の値
	 * パラメータ＝年休付与テーブル：IS00280の値
	 * パラメータ＝労働条件の期間：NULL
	 * パラメータ＝契約時間：NULL
	 * パラメータ＝入社年月日：NULL
	 * @return 次回年休付与日, 次回年休付与日数, 次回時間年休付与上限
	 */
	@POST
	@Path("nextTime")
	public NextTimeEventDto getNextTimeData(NextTimeEventParam param) {
		if (param.getStandardDate() == null){
			return new NextTimeEventDto();
		}
		AnnLeaEmpBasicInfo annLea = new AnnLeaEmpBasicInfo(param.getEmployeeId(),
				GeneralDate.fromString(param.getStandardDate(), "yyyy/MM/dd"), param.getGrantTable(),
				new DatePeriod(param.getStartWorkCond(), param.getEndWorkCond()), param.getContactTime(),
				param.getEntryDate());
		return NextTimeEventDto.fromDomain(getYearHolidayInfo.getYearHolidayInfo(annLea));
	}

}
