package nts.uk.screen.com.ws.ccg005;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.com.app.find.ccg005.PermissionSettingDto;
import nts.uk.screen.com.app.find.ccg005.PermissionSettingsScreenQuery;
import nts.uk.screen.com.app.find.ccg005.attendance.data.DisplayAttendanceDataDto;
import nts.uk.screen.com.app.find.ccg005.attendance.data.DisplayAttendanceDataScreenQuery;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationScreenQuery;
import nts.uk.screen.com.app.find.ccg005.attendance.information.EmpIdParam;
import nts.uk.screen.com.app.find.ccg005.display.information.DisplayInformationDto;
import nts.uk.screen.com.app.find.ccg005.display.information.DisplayInformationScreenQuery;
import nts.uk.screen.com.app.find.ccg005.favorite.information.FavoriteInformationScreenQuery;
import nts.uk.screen.com.app.find.ccg005.favorite.information.FavoriteSpecifyDto;
import nts.uk.screen.com.app.find.ccg005.goout.GoOutEmployeeInformationDto;
import nts.uk.screen.com.app.find.ccg005.goout.GoOutInformationScreenQuery;
import nts.uk.screen.com.app.find.ccg005.search.employee.SearchEmployeeDto;
import nts.uk.screen.com.app.find.ccg005.search.employee.SearchEmployeeScreenQuery;
import nts.uk.screen.com.ws.ccg005.attendance.AttendanceInformationParam;
import nts.uk.screen.com.ws.ccg005.display.information.DisplayInfoAfterSelectParam;
import nts.uk.screen.com.ws.ccg005.goout.GoOutInformationParam;
import nts.uk.shr.com.context.AppContexts;


@Path("screen/com/ccg005")
@Produces("application/json")
public class Ccg005Ws {

	@Inject
	private DisplayAttendanceDataScreenQuery dispDataSq;
	
	@Inject
	private AttendanceInformationScreenQuery attendaceInfoSq;
	
	@Inject
	private DisplayInformationScreenQuery dispInfoSq;
	
	@Inject
	private SearchEmployeeScreenQuery searchEmpSq;
	
	@Inject
	private GoOutInformationScreenQuery goOutInfoSq;
	
	@Inject
	private FavoriteInformationScreenQuery favoriteInfoSq;
	
	@Inject 
	private PermissionSettingsScreenQuery permissionSettingsSq;
	
	@POST
	@Path("get-display-attendance-data")
	public DisplayAttendanceDataDto getDisplayAttendanceData() {
	 	return dispDataSq.getDisplayAttendanceData();
	}
	
	@POST
	@Path("get-attendance-information")
	public List<AttendanceInformationDto> getAttendanceInformation(AttendanceInformationParam params) {
		EmpIdParam loginEmp = EmpIdParam.builder()
				.sid(AppContexts.user().employeeId())
				.pid(AppContexts.user().personId())
				.build();
		if (!params.getEmpIds().contains(loginEmp)) {
			params.getEmpIds().add(loginEmp);
		}
	 	return attendaceInfoSq.getAttendanceInformation(
	 			params.getEmpIds(),
	 			params.getBaseDate(),
	 			params.isEmojiUsage()
	 			);
	}
	
	@POST
	@Path("get-information-after-select")
	public DisplayInformationDto getDisplayInfoAfterSelect(DisplayInfoAfterSelectParam params) {
	 	return dispInfoSq.getDisplayInfoAfterSelect(
	 			params.getWkspIds(),
	 			params.getBaseDate(),
	 			params.isEmojiUsage()
	 			);
	}
	
	@POST
	@Path("get-employee-search")
	public SearchEmployeeDto searchForEmployee(SearchForEmployeeParam params) {
	 	return searchEmpSq.searchForEmployee(
	 			params.getKeyWorks(),
	 			params.getBaseDate(),
	 			params.isEmojiUsage()
	 			);
	}
	
	@POST
	@Path("get-go-out-information")
	public GoOutEmployeeInformationDto getGoOutInformation(GoOutInformationParam params) {
	 	return goOutInfoSq.getGoOutInformation(
	 			params.getSid(),
	 			params.getDate()
	 			);
	}
	
	@POST
	@Path("get-favorite-information")
	public List<FavoriteInformationData> getFavoriteInformation() {
		Map<FavoriteSpecifyDto, List<String>> map = favoriteInfoSq.getFavoriteInformation();
		List<FavoriteInformationData> returnList = new ArrayList<>();
		map.forEach((key, value) -> {
			FavoriteInformationData data = FavoriteInformationData.builder()
					.favoriteName(key.getFavoriteName())
					.creatorId(key.getCreatorId())
					.inputDate(key.getInputDate())
					.targetSelection(key.getTargetSelection())
					.workplaceId(key.getWorkplaceId())
					.order(key.getOrder())
					.wkspNames(value)
					.build();
			returnList.add(data);
		});
		returnList.sort(Comparator.comparing(FavoriteInformationData::getOrder));
		return returnList;
	}
	
	@POST
	@Path("get-permission-settings")
	public PermissionSettingDto getPermissionSetting() {
		return this.permissionSettingsSq.getPermissionSetting();
	}
}
