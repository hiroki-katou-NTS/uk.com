package nts.uk.ctx.at.record.ws.kdp.kdp010.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.TimeStampInputSettingsCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.PortalStampSettingsCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsSmartphoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsUsingEmbossingCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.StampSetCommunalCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.StampSettingOfRICOHCopierCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;

@Path("at/record/stamp/timestampinputsetting")
@Produces("application/json")
public class TimeStampInputSettingsCommandWebService extends WebService {

	@Inject
	private TimeStampInputSettingsCommandHandler commandHandler;
	
	/**打刻の前準備(ポータル)を登録する */
	@POST
	@Path("portalstampsettings/save")
	public void savePortalStampSettings(PortalStampSettingsCommand command) {
		commandHandler.savePortalStampSettings(command);
	}
	
	/**打刻の前準備(共有)を登録する */
	@POST
	@Path("stampsetcommunal/save")
	public void saveStampSetCommunal(StampSetCommunalCommand command) {
		commandHandler.saveStampSetCommunal(command);
	}
	
	/**打刻の前準備(スマホ)を登録する */
	@POST
	@Path("settingssmartphonestamp/save")
	public void saveSettingsSmartphoneStamp(SettingsSmartphoneStampCommand command) {
		commandHandler.saveSettingsSmartphoneStamp(command);
	}
	
	/**打刻の前準備(利用設定)を登録する*/
	@POST
	@Path("settingsusingembossing/save")
	public void saveSettingsUsingEmbossing(SettingsUsingEmbossingCommand command) {
		commandHandler.saveSettingsUsingEmbossing(command);
	}
	
	@POST
	@Path("stampsettingofRICOHcopier/save")
	public void saveStampSettingOfRICOHCopier(StampSettingOfRICOHCopierCommand command) {
		commandHandler.saveStampSettingOfRICOHCopier(command);
	}
	
	@POST
	@Path("saveStampPage")
	public void saveStampPageLayout(StampPageLayoutCommand command) {
		commandHandler.saveStampPageLayout(command);
	}
	
	@POST
	@Path("smartphonepagelayoutsettings/del")
	public void delPageLayoutSettingsSmartphone() {
		commandHandler.delPageLayoutSettingsSmartphone();
	}
}


