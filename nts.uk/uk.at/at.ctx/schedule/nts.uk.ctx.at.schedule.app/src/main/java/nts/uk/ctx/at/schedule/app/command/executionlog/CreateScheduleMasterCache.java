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
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
public class CreateScheduleMasterCache {

	private final EmployeeGeneralInfoImported empGeneralInfo;
	private final Map<String, List<EmploymentInfoImported>> mapEmploymentStatus;
	private final List<WorkCondItemDto> listWorkingConItem;
	private final List<ShortWorkTimeDto> listShortWorkTimeDto;
	private final List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis;
	
	private final List<WorkType> listWorkType = new ArrayList<>();
	private final List<WorkTimeSetting> listWorkTimeSetting = new ArrayList<>();
	private final Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting = new HashMap<>();
	private final Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting = new HashMap<>();
	private final Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting = new HashMap<>();
	
	public CreateScheduleMasterCache(
			EmployeeGeneralInfoImported empGeneralInfo,
			Map<String, List<EmploymentInfoImported>> mapEmploymentStatus,
			List<WorkCondItemDto> listWorkingConItem,
			List<ShortWorkTimeDto> listShortWorkTimeDto,
			List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis) {
		
		this.empGeneralInfo = empGeneralInfo;
		this.mapEmploymentStatus = mapEmploymentStatus;
		this.listWorkingConItem = listWorkingConItem;
		this.listShortWorkTimeDto = listShortWorkTimeDto;
		this.listBusTypeOfEmpHis = listBusTypeOfEmpHis;
	}
	
	public ShortWorkTimeDto.List getShortWorkTimeDtos() {
		return new ShortWorkTimeDto.List(listShortWorkTimeDto);
	}
	
	public BusinessTypeOfEmpDto.List getBusinessTypeOfEmpDtos() {
		return new BusinessTypeOfEmpDto.List(listBusTypeOfEmpHis);
	}
}
