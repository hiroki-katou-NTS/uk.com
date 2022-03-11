package nts.uk.screen.com.ws.cmf.cmf001;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.screen.com.app.cmf.cmf001.f.get.CsvBasedImportSettingDto;
import nts.uk.screen.com.app.cmf.cmf001.f.get.GetCsvBasedImportSetting;
import nts.uk.screen.com.app.cmf.cmf001.f.save.Cmf001fSaveCommand;
import nts.uk.screen.com.app.cmf.cmf001.f.save.Cmf001fSaveCommandHandler;

@Path("screen/com/cmf/cmf001/f")
@Produces(MediaType.APPLICATION_JSON)
public class Cmf001fWebService {
	
	@Inject
	private GetCsvBasedImportSetting getSetting;
	
	@POST
	@Path("setting/{code}")
	public CsvBasedImportSettingDto getSetting(@PathParam("code") String code) {
		return getSetting.get(new ExternalImportCode(code));
	}
	
	@Inject
	private Cmf001fSaveCommandHandler saveHandler;
	
	@POST
	@Path("save")
	public void save(Cmf001fSaveCommand command) {
		saveHandler.handle(command);
	}
}
