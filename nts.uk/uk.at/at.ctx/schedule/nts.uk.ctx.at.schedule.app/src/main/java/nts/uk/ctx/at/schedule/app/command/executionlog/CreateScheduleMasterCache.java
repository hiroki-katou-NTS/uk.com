package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.WorkRestTimeZoneDto;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
public class CreateScheduleMasterCache {
	//特定期間の社員情報 -> 職場履歴一覧,雇用履歴一覧,職位履歴一覧,分類履歴一覧
	private final EmployeeGeneralInfoImported empGeneralInfo;
	//社員の在職状態一覧
	// Tạm thời không sử dụng cái này, chuyển sang dùng 社員の予定管理状態一覧
	private final Map<String, List<EmploymentInfoImported>> mapEmploymentStatus;
	//労働条件情報 : 労働条件 
	//労働条件一覧
	private final List<WorkCondItemDto> listWorkingConItem;
	//社員の短時間勤務履歴一覧 
	//社員の短時間勤務一覧 theo EA mới
	private final List<ShortWorkTimeDto> listShortWorkTimeDto;
	//特定期間の社員情報 -> 社員ID,勤務種別一覧,部門履歴一覧
	private final List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis;
	//勤務種類一覧
	private final List<WorkType> listWorkType;
	//就業時間帯一覧
	private final List<WorkTimeSetting> listWorkTimeSetting = new ArrayList<>();
	//固定勤務設定一覧
	private final Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting = new HashMap<>();
	//流動勤務設定一覧
	private final Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting = new HashMap<>();
	//時差勤務設定一覧
	private final Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting = new HashMap<>();
	//社員の予定管理状態一覧
	private final List<ScheManaStatuTempo> listManaStatuTempo;
	
	public CreateScheduleMasterCache(
			EmployeeGeneralInfoImported empGeneralInfo,
			Map<String, List<EmploymentInfoImported>> mapEmploymentStatus,
			List<WorkCondItemDto> listWorkingConItem,
			List<ShortWorkTimeDto> listShortWorkTimeDto,
			List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis,
			List<WorkType> listWorkType,
			List<ScheManaStatuTempo> listManaStatuTempo) {
		
		this.empGeneralInfo = empGeneralInfo;
		this.mapEmploymentStatus = mapEmploymentStatus;
		this.listWorkingConItem = listWorkingConItem;
		this.listShortWorkTimeDto = listShortWorkTimeDto;
		this.listBusTypeOfEmpHis = listBusTypeOfEmpHis;
		this.listWorkType = listWorkType;
		this.listManaStatuTempo = listManaStatuTempo;
	}
	
	public ShortWorkTimeDto.List getShortWorkTimeDtos() {
		return new ShortWorkTimeDto.List(listShortWorkTimeDto);
	}
	
	public BusinessTypeOfEmpDto.List getBusinessTypeOfEmpDtos() {
		return new BusinessTypeOfEmpDto.List(listBusTypeOfEmpHis);
	}
	
}
