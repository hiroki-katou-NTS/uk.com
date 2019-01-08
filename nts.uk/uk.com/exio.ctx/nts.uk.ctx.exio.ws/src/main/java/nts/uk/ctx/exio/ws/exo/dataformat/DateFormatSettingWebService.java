package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.dataformat.DateFormatSettingCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.DateFormatSettingCommandHandler;
import nts.uk.ctx.exio.app.find.exo.dataformat.DateFormatSettingDTO;
import nts.uk.ctx.exio.app.find.exo.dataformat.DateFormatSettingFinder;

@Path("exio/exo/dateformat")
@Produces("application/json")
public class DateFormatSettingWebService {

	@Inject
	private DateFormatSettingCommandHandler dateFormatSettingCommandHandler;

	@Inject
	private DateFormatSettingFinder dateFormatSettingFinder;

	@POST
	@Path("addDateFormatSetting")
	public void addDateFormatSetting(DateFormatSettingCommand command) {
		this.dateFormatSettingCommandHandler.handle(command);
	}

	@POST
	@Path("getDateFormatSettingByCid")
	public DateFormatSettingDTO getDateFormatSetting() {
		return this.dateFormatSettingFinder.getDateFormatSettingByCid();
	}
}
