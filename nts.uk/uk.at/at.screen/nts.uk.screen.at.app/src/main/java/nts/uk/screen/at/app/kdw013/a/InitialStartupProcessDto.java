package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.DivergenceReasonInputMethodDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DivergenceTimeRootDto;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.DailyAttendanceItemDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.screen.at.app.kdw013.query.AttItemNameDto;
import nts.uk.screen.at.app.kdw013.query.AttendanceItemMasterInformationDto;
import nts.uk.screen.at.app.kdw013.query.FavoriteTaskDisplayOrderDto;
import nts.uk.screen.at.app.kdw013.query.FavoriteTaskItemDto;
import nts.uk.screen.at.app.kdw013.query.GetFavoriteTaskDto;
import nts.uk.screen.at.app.kdw013.query.OneDayFavoriteSetDto;
import nts.uk.screen.at.app.kdw013.query.OneDayFavoriteTaskDisplayOrderDto;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialStartupProcessDto {
	/** 作業枠利用設定 */
	private TaskFrameUsageSettingDto taskFrameUsageSetting;

	/** List<作業> */
	private List<TaskDto> tasks;

	// 勤怠項目リスト：List<勤怠項目>
	public List<AttItemNameDto> attItemName;

	// 日次の勤怠項目リスト：List<日次の勤怠項目>
	public List<DailyAttendanceItemDto> dailyAttendanceItem;

	// 勤務種類リスト：List<勤務種類>
	public List<WorkTypeDto> workTypes;

	// 就業時間帯リスト：List<就業時間帯の設定>
	public List<WorkTimeSettingDto> workTimeSettings;

	// 乖離時間リスト：List<乖離時間>
	public List<DivergenceTimeRootDto> divergenceTimeRoots;

	// 乖離理由リスト：List<乖離理由の入力方法>
	public List<DivergenceReasonInputMethodDto> divergenceReasonInputMethods;

	/** 社員の所属情報(Map<社員ID,職場ID>) */
	private Map<String, String> employeeInfos;

	/** List＜社員ID（List）から社員コードと表示名を取得＞ */
	private List<EmployeeBasicInfoDto> lstEmployeeInfo;

	/** List＜職場情報一覧＞ */
	private List<WorkplaceInfo> workplaceInfos;

	// Get*(ログイン社員ID)
	// 1日お気に入り作業セット
	public List<OneDayFavoriteSetDto> oneDayFavSets;

	// 1日お気に入り作業の表示順
	public OneDayFavoriteTaskDisplayOrderDto oneDayFavTaskDisplayOrders;

	// お気に入り作業項目
	public List<FavoriteTaskItemDto> favTaskItems;

	// お気に入り作業の表示順
	public FavoriteTaskDisplayOrderDto favTaskDisplayOrders;

	public void setManHourInput(StartManHourInput domain) {
		
		this.taskFrameUsageSetting = new TaskFrameUsageSettingDto(domain.getTaskFrameUsageSetting()
				.getFrameSettingList().stream().map(x -> TaskFrameSettingDto.toDto(x)).collect(Collectors.toList()));
		
		this.tasks = domain.getTasks().stream().map(x -> TaskDto.toDto(x)).collect(Collectors.toList());
		
	}

	public void setItemMasterInfo(AttendanceItemMasterInformationDto itemMasterInfo) {
		
		this.attItemName = itemMasterInfo.getAttItemName();
		this.dailyAttendanceItem = itemMasterInfo.getDailyAttendanceItem();
		this.workTypes = itemMasterInfo.getWorkTypes();
		this.workTimeSettings = itemMasterInfo.getWorkTimeSettings();
		this.divergenceTimeRoots = itemMasterInfo.getDivergenceTimeRoots();
		this.divergenceReasonInputMethods = itemMasterInfo.getDivergenceReasonInputMethods();
		
	}

	public void setRefWork(GetRefWorkplaceAndEmployeeDto refWork) {
		
		this.employeeInfos = refWork.getEmployeeInfos();
		this.lstEmployeeInfo = refWork.getLstEmployeeInfo();
		this.workplaceInfos = refWork.getWorkplaceInfos();
		
	}

	public void setFavTask(GetFavoriteTaskDto favTask) {
		this.oneDayFavSets = favTask.getOneDayFavSets();
		this.oneDayFavTaskDisplayOrders = favTask.getOneDayFavTaskDisplayOrders();
		this.favTaskItems = favTask.getFavTaskItems();
		this.favTaskDisplayOrders = favTask.getFavTaskDisplayOrders();
	}
}
