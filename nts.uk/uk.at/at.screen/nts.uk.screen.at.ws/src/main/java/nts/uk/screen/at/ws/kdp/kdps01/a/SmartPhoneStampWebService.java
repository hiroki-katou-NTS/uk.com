package nts.uk.screen.at.ws.kdp.kdps01.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.kdp.kdp004.a.StampButtonCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.CheckCanUseSmartPhoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.CheckCanUseSmartPhoneStampCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.CheckCanUseSmartPhoneStampResult;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.RegisterSmartPhoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.RegisterSmartPhoneStampCommandHandler;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.screen.at.app.query.kdp.kdps01.a.GetOmissionContent;
import nts.uk.screen.at.app.query.kdp.kdps01.a.GetOmissionContentDto;
import nts.uk.screen.at.app.query.kdp.kdps01.a.GetSettingSmartPhone;
import nts.uk.screen.at.app.query.kdp.kdps01.a.SettingSmartPhoneDto;
import nts.uk.screen.at.app.query.kdp.kdps01.a.SuppressingStampButton;

@Path("at/record/stamp/smart-phone")
@Produces("application/json")
public class SmartPhoneStampWebService extends WebService {

	@Inject
	private CheckCanUseSmartPhoneStampCommandHandler checkCanUseHandler;

	@Inject
	private GetSettingSmartPhone getSettingSmartPhone;

	@Inject
	private RegisterSmartPhoneStampCommandHandler registerCommand;
	
	@Inject
	private SuppressingStampButton suppressButton;

	@Inject
	private GetOmissionContent getOmission;

	/**
	 * 打刻入力(スマホ)の実行可能判定を行う
	 */

	@POST
	@Path("check-can-use-stamp")
	public CheckCanUseSmartPhoneStampResult checkCanUseStamp(CheckCanUseSmartPhoneStampCommand command) {
		return this.checkCanUseHandler.handle(command);
	}

	/**
	 * 打刻入力(スマホ)を起動する
	 */
	@POST
	@Path("get-setting")
	public SettingSmartPhoneDto GetSetting() {

		return this.getSettingSmartPhone.GetSetting();
	}

	/**
	 * 打刻入力(スマホ)を登録する
	 */
	@POST
	@Path("register-stamp")
	public GeneralDate registerStamp(RegisterSmartPhoneStampCommand command) {

		return this.registerCommand.handle(command);
	}

	/**
	 * 打刻入力(スマホ)の打刻ボタンを抑制の表示をする
	 */
	@POST
	@Path("get-suppress")
	public StampToSuppress getSuppressing() {

		return this.suppressButton.getSuppressingStampButton();
	}

	/**
	 * 打刻入力(スマホ)の打刻漏れの内容を取得する
	 */
	
	@POST
	@Path("get-omission")
	public GetOmissionContentDto getOmission(StampButtonCommand query) {

		return this.getOmission.getOmission(query);
	}
}
