package nts.uk.ctx.at.function.ws.alarm.mailsettings;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.alarm.mailsettings.MailAutoAndNormalCommand;
import nts.uk.ctx.at.function.app.command.alarm.mailsettings.MailSettingHandler;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailAutoAndNormalDto;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingFinder;

/**
 * @author thanhpv
 *
 */

@Path("at/function/alarm")
@Produces("application/json")
public class MailAutoNormalSetting extends WebService{

	@Inject
	private MailSettingFinder finder;
	
	@Inject
	private MailSettingHandler CommandHandler;
	
	@POST
	@Path("mailsetting/getinformailseting")
	public MailAutoAndNormalDto getInforMailSeting(){
		return finder.findMailSet();
	}
	
	@POST
	@Path("mailSetting/addMailSetting")
	public void insert(MailAutoAndNormalCommand command){
		CommandHandler.addMailSetting(command);
	}
}
