package nts.uk.screen.at.ws.knr.knr002.e;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nts.uk.screen.at.app.query.knr.knr002.e.BackupContentDto;
import nts.uk.screen.at.app.query.knr.knr002.e.GetBackup;
import nts.uk.screen.at.app.query.knr.knr002.e.GetBackupContent;
import nts.uk.screen.at.app.query.knr.knr002.e.GetLocationSetting;
import nts.uk.screen.at.app.query.knr.knr002.e.InitialDisplayBackupScreen;
import nts.uk.screen.at.app.query.knr.knr002.e.InitialDisplayBackupScreenDto;
import nts.uk.screen.at.app.query.knr.knr002.e.LocationSettingDto;

@Path("screen/knr002/e")
public class ScreenEWS {

	@Inject
	private InitialDisplayBackupScreen initialDisplayBackupScreenSC;
	
	@Inject
	private GetBackup getBackupSC;
	
	@Inject
	private GetBackupContent getBackupContentSC;
	
	@Inject
	private GetLocationSetting getLocationSettingSC;
	
	@POST
	@Path("initialDisplayBackupScreen")
	public InitialDisplayBackupScreenDto getInitialDisplayBackupScreen() {
		return this.initialDisplayBackupScreenSC.handle();
	}
	
	@POST
	@Path("getBackup/{code}")
	public void getBackup(@PathParam("code") String code) {
		this.getBackupSC.handle(code);
	}
	
	@POST
	@Path("getBackupContent/{code}")
	public List<BackupContentDto> getBackupContent(@PathParam("code") String code) {
		return this.getBackupContentSC.handle(code);
	}
	
	@POST
	@Path("getLocationSetting/{code}")
	public List<LocationSettingDto> getLocationSetting(@PathParam("code") String code) {
		return this.getLocationSettingSC.handle(code);
	}
	
}
