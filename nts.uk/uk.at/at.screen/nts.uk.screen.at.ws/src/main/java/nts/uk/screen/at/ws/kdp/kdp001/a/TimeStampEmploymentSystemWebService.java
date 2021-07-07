package nts.uk.screen.at.ws.kdp.kdp001.a;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.ConfirmUseOfStampInputCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.ConfirmUseOfStampInputCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.ConfirmUseOfStampInputResult;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.GetOmissionContentQuery;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.screen.at.app.command.kdp.kdp001.a.RegisterStampInputCommand;
import nts.uk.screen.at.app.command.kdp.kdp001.a.RegisterStampInputCommandHandler;
import nts.uk.screen.at.app.command.kdp.kdp001.a.RegisterStampInputResult;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplayListStampForStampInput;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplaySuppressStampButtonInStampInput;
import nts.uk.screen.at.app.query.kdp.kdp001.a.GetSettingStampInput;
import nts.uk.screen.at.app.query.kdp.kdp001.a.LocationStampInput;
import nts.uk.screen.at.app.query.kdp.kdp001.a.LocationStampInputDto;
import nts.uk.screen.at.app.query.kdp.kdp001.a.LocationStampInputParam;
import nts.uk.screen.at.app.query.kdp.kdp001.a.SettingPotalStampInputDto;
import nts.uk.screen.at.app.query.kdp.kdp002.a.DailyAttdErrorInfoDto;
import nts.uk.screen.at.app.query.kdp.kdp002.a.GetOmissionContentsFinder;

@Path("at/record/stamp/employment_system")
@Produces("application/json")
public class TimeStampEmploymentSystemWebService extends WebService {

	@Inject
	private DisplayListStampForStampInput displayListStamp;

	@Inject
	private ConfirmUseOfStampInputCommandHandler confirmHandler;

	@Inject
	private RegisterStampInputCommandHandler registerStampHandler;

	@Inject
	private GetSettingStampInput getSettingStampInput;

	@Inject
	private GetOmissionContentsFinder omissionContentsFinder;

	@Inject
	private DisplaySuppressStampButtonInStampInput displaySuppressStampButtonInStampInput;
	
	@Inject
	private LocationStampInput locationStampInput;

	/**
	 * 打刻入力(ポータル)の打刻履歴一覧を表示する
	 */
	@POST
	@Path("get_employee_stamp_data")
	public List<StampDataOfEmployeesDto> getEmployeeStampData() {
		return this.displayListStamp.getEmployeeStampData();
	}

	/**
	 * 打刻入力(ポータル)の利用確認を行う
	 */
	@POST
	@Path("confirm_use_of_stamp_input")
	public ConfirmUseOfStampInputResult confirmUseOfStampInput(ConfirmUseOfStampInputCommand command) {

		return this.confirmHandler.handle(command);

	}

	/**
	 * 打刻入力(ポータル)を登録する
	 */
	@POST
	@Path("register_stamp_input")
	public RegisterStampInputResult registerStampInput(RegisterStampInputCommand command) {
		return this.registerStampHandler.handle(command);

	}

	/**
	 * 打刻入力(ポータル)を起動する
	 */
	@POST
	@Path("get_setting_stamp_input")
	public SettingPotalStampInputDto getSettingStampInput() {

		return this.getSettingStampInput.getSettingPotalStampInput();
	}

	/**
	 * 打刻入力(ポータル)の打刻漏れの内容を取得する
	 */
	@POST
	@Path("get_omission_contents")
	public DailyAttdErrorInfoDto getOmissionContents(GetOmissionContentQuery query) {

		return this.omissionContentsFinder.getOmissionContents(query.getPageNo(), query.getButtonDisNo(),
				query.getStampMeans());
	}

	/**
	 * 打刻入力(ポータル)の打刻ボタンを抑制の表示をする
	 */
	@POST
	@Path("get_stamp_to_suppress")
	public StampToSuppress getStampToSuppress() {
		return this.displaySuppressStampButtonInStampInput.getStampToSuppress();
	}

	/**
	 * 打刻入力の場所を取得する
	 * @return
	 */
	@POST
	@Path("get_location_stamp_input")
	public LocationStampInputDto getLocationStampInput(LocationStampInputParam param) {
		return this.locationStampInput.get(param);
	}
}