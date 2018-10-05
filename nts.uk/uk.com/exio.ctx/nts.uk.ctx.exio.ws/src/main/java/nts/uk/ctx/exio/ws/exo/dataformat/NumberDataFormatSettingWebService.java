package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.dataformat.NumberDataFormatSettingCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.NumberDataFormatSettingCommandHandler;
import nts.uk.ctx.exio.app.find.exo.dataformat.NumberDataFormatSettingDTO;
import nts.uk.ctx.exio.app.find.exo.dataformat.NumberDataFormatSettingFinder;

@Path("exio/exo/numberformat")
@Produces("application/json")
public class NumberDataFormatSettingWebService {

	@Inject
	private NumberDataFormatSettingCommandHandler numberDataFormatSettingCommandHandler;

	@Inject
	private NumberDataFormatSettingFinder numberDataFormatSettingFinder;

	@POST
	@Path("addNumberFormatSetting")
	public void addNumberFormatSetting(NumberDataFormatSettingCommand command) {
		this.numberDataFormatSettingCommandHandler.handle(command);
	}

	@POST
	@Path("getNumberFormatSettingByCid")
	public NumberDataFormatSettingDTO getNumberFormatSettingByCid() {
		return this.numberDataFormatSettingFinder.getNumberDataFormatSettingByCid();
	}
}
