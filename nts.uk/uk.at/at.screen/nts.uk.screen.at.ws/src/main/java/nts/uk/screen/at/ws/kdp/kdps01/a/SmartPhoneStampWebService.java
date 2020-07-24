package nts.uk.screen.at.ws.kdp.kdps01.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.CheckCanUseSmartPhoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.CheckCanUseSmartPhoneStampCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdps01.a.CheckCanUseSmartPhoneStampResult;
import nts.uk.screen.at.app.query.kdp.kdps01.a.GetSettingSmartPhone;
import nts.uk.screen.at.app.query.kdp.kdps01.a.SettingSmartPhoneDto;

@Path("at/record/stamp/smart-phone")
@Produces("application/json")
public class SmartPhoneStampWebService extends WebService {

	@Inject
	private CheckCanUseSmartPhoneStampCommandHandler checkCanUseHandler;
	
	@Inject
	private GetSettingSmartPhone getSettingSmartPhone;

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
}
