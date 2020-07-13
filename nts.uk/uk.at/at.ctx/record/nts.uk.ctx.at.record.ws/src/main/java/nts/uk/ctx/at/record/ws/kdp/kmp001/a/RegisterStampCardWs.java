package nts.uk.ctx.at.record.ws.kdp.kmp001.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.RegisterStampCardCommandHandler;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.a.CardInformationCommands;
import nts.uk.ctx.at.record.app.command.kmp.kmp001.a.EmployeeCardInformationViewACommand;

/**
 * 
 * @author chungnt
 *
 */

@Path("at/record/register-stamp-card")
@Produces("application/json")
public class RegisterStampCardWs extends WebService {

	@Inject
	private RegisterStampCardCommandHandler commandHandler;
	
	/**新規モード時にIDカードNOの登録を行う */
	@POST
	@Path("view-a/save")
	public void saveStampCardViewA(EmployeeCardInformationViewACommand command) {
		commandHandler.saveStampCardViewA(command);
	}
	
	/**新規モード時にIDカードNOの登録を行う */
	@POST
	@Path("view-a/update")
	public void updateStampCardViewA(EmployeeCardInformationViewACommand command) {
		commandHandler.updateStampCardViewA(command);
	}
	
	/**新規モード時にIDカードNOの登録を行う */
	@POST
	@Path("view-a/save/delete")
	public void deleteStampCardViewA(CardInformationCommands command) {
		commandHandler.deleteCardInfomaiton(command);
	}
	
}
