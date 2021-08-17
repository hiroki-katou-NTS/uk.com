package nts.uk.screen.com.ws.cmf.cmf001;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportLayoutDto;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingDto;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingFinder;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingListItemDto;
import nts.uk.screen.com.app.cmf.cmf001.b.get.FindExternalImportSettingLayoutQuery;
import nts.uk.screen.com.app.cmf.cmf001.b.save.Cmf001bSaveCommand;
import nts.uk.screen.com.app.cmf.cmf001.b.save.Cmf001bSaveCommandHandler;


@Path("screen/com/cmf/cmf001/setting")
@Produces(MediaType.APPLICATION_JSON)
public class Cmf001bWebService {
	
	@Inject
	private ExternalImportSettingFinder finder;
	
	@Inject
	private Cmf001bSaveCommandHandler saveCmd;
	
	@POST
	@Path("find-all")
	public List<ExternalImportSettingListItemDto> findAll() {
		List<ExternalImportSettingListItemDto> result = finder.findAll();
		return result;
	}
	
	@POST
	@Path("find/{settingCode}")
	public ExternalImportSettingDto find(@PathParam("settingCode") String settingCode) {
		ExternalImportSettingDto result = finder.find(settingCode);
		return result;
	}
	
	@POST
	@Path("find/layout")
	public List<ExternalImportLayoutDto> findLayout(FindExternalImportSettingLayoutQuery query) {
		List<ExternalImportLayoutDto> result = finder.findLayout(query);
		return result;
	}
	
	@POST
	@Path("save")
	public void save(Cmf001bSaveCommand command) {
		saveCmd.handle(command);
	}
}
