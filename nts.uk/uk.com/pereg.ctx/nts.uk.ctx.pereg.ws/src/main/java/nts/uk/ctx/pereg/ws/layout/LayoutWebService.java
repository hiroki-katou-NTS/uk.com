package nts.uk.ctx.pereg.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.remainnumber.YearHolidayInfoResultDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.empinfo.basicinfo.SpecialleaveInformation;
import nts.uk.ctx.at.shared.app.find.remainingnumber.empinfo.basicinfo.SpecialleaveInformationFinder;
import nts.uk.ctx.at.shared.app.find.remainingnumber.rervleagrtremnum.ResvLeaRemainNumberFinder;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject.AnnLeaEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainService;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutWebService extends WebService {

	@Inject
	private RegisterLayoutFinder layoutFinder;
	
	@Inject
	private PeregProcessor layoutProcessor;

	@Inject 
	private SpecialLeaveGrantRemainService specialLeaveGrantRemainService;
	
	@Inject
	private ResvLeaRemainNumberFinder resvLeaNumberFinder;
	
	@Inject
	private SpecialleaveInformationFinder specialleaveInformationFinder;
	
	@Inject 
	private AnnLeaEmpBasicInfoDomService annLeaEmpBasicInfoDomService;
	
	@Path("getByCreateType")
	@POST
	public NewLayoutDto getByCreateType(AddEmployeeCommand command) {
		return this.layoutFinder.getLayoutByCreateType(command);
	}
	
	/**
	 * get category and it's children
	 * @author xuan vinh
	 * 
	 * @param ctgId
	 * @return
	 */
	
	@Path("find/getctgtab/{categoryid}")
	@POST
	public List<PerInfoCtgFullDto> getCtgTab(@PathParam("categoryid")String ctgId){
		return this.layoutProcessor.getCtgTab(ctgId);
	}
	
	/**
	 * @author xuan vinh
	 * @param query
	 * @return
	 */
	
	@Path("find/gettabdetail")
	@POST
	public EmpMaintLayoutDto getTabDetail(PeregQuery query){
		return this.layoutProcessor.getCategoryDetail(query);
	}
	
	
	@Path("calDayTime/{sid}/{specialCD}")
	@POST
	public Object calDayTime(@PathParam("sid")String sid , @PathParam("specialCD")int specialCD){
		String dayTime = specialLeaveGrantRemainService.calDayTime(sid, specialCD);
		return new Object[] {dayTime};
	}
	
	@POST
	@Path("getResvLeaNumber/{sid}")
	public Object getResvLeaNumber(@PathParam("sid") String employeeId) {
		String dayNumber = resvLeaNumberFinder.getResvLeaRemainNumber(employeeId);
		return new Object[] {dayNumber};
	}
	/**
	 * Category Special Holiday CS00025 ~
	 * 次回特休情報を取得する
	 * @param 社員ID sid
	 * @param 特別休暇コード specialCD
	 * @param 特休付与基準日 referDate
	 * @param 適用設定 appSet
	 * @param 特休付与テーブルコード grantTableCD
	 * @param 付与日数 grantedDays
	 * @param 入社年月日 entryDate NULL
	 * @param 退職年月日 retireDate NULL
	 * @param 年休付与基準日 year NULL
	 * @return 次回付与日 grantDate
	 */
	@POST
	@Path("getSPHolidayGrantDate")
	public GeneralDate getSPHolidayGrantDate(SpecialleaveInformation specialLeaveInfo){
		return specialleaveInformationFinder.getSPHolidayGrantDate(specialLeaveInfo);
	}
	
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
	@Path("getYearHolidayInfo")
	public YearHolidayInfoResultDto getYearHolidayInfo(AnnLeaEmpBasicInfo annLea){
		return YearHolidayInfoResultDto.fromDomain(annLeaEmpBasicInfoDomService.getYearHolidayInfo(annLea));
	}
	
}
