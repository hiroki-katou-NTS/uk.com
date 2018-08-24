package nts.uk.ctx.at.shared.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.nexttime.NextTimeEventDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.nexttime.NextTimeEventParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject.AnnLeaEmpBasicInfo;

@Path("at/record/remainnumber/annlea/event")
@Produces("application/json")
public class NextTimeEventService extends WebService{
	
	@Inject 
	private AnnLeaEmpBasicInfoDomService annLeaEmpBasicInfoDomService;
	
	/**
	 * Category: CS00024
	 * アルゴリズム「次回年休情報を取得する」
　	 * パラメータ＝社員ID：画面で選択している社員ID
　	 * パラメータ＝年休付与基準日：IS00279の値
	 * パラメータ＝年休付与テーブル：IS00280の値
	 * パラメータ＝労働条件の期間：NULL
	 * パラメータ＝契約時間：NULL
	 * パラメータ＝入社年月日：NULL
	 * パラメータ＝退職年月日：NULL
	 * @return 次回年休付与日, 次回年休付与日数, 次回時間年休付与上限
	 */
	@POST
	@Path("nextTime")
	public NextTimeEventDto getNextTimeData(NextTimeEventParam param) {
		if (param.getStandardDate() == null){
			return new NextTimeEventDto();
		}
		AnnLeaEmpBasicInfo annLea = new AnnLeaEmpBasicInfo(param.getEmployeeId(),
				GeneralDate.fromString(param.getStandardDate(), "yyyy/MM/dd"), param.getGrantTable(), null, null, null,
				null);
		return NextTimeEventDto.fromDomain(annLeaEmpBasicInfoDomService.getYearHolidayInfo(annLea));
	}

}
