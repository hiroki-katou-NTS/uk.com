package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Getter
public class MasterCache {

	private final List<WorkType> workTypes;
	private final List<WorkTimeSetting> workTimeSettings;
	private final Map<String, WorkType> workTypesMap;
	private final Map<String, WorkTimeSetting> workTimeSettingsMap;
	
	public MasterCache(
			String companyId,
			List<BasicSchedule> basicScheduleList,
			WorkTypeRepository workTypeRepo,
			WorkTimeSettingRepository workTimeSettingRepo) {
		
		List<String> listWorkTypeCode = basicScheduleList.stream().map(x -> {
			return x.getWorkTypeCode();
		}).distinct().collect(Collectors.toList());
		List<String> listWorkTimeCode = basicScheduleList.stream().map(x -> {
			return x.getWorkTimeCode();
		}).distinct().collect(Collectors.toList());
		
		// 勤務種類を取得する (Lấy dữ lieu loại làm việc)
		workTypes = workTypeRepo.getPossibleWorkType(companyId, listWorkTypeCode);
		// 就業時間帯を取得する (Lấy dữ liệu thời gian làm việc)
		workTimeSettings = workTimeSettingRepo.findByCodes(companyId, listWorkTimeCode);

		workTypesMap = workTypes.stream().collect(Collectors.toMap(x -> {
			return x.getWorkTypeCode().v();
		}, x -> x));

		workTimeSettingsMap = workTimeSettings.stream().collect(Collectors.toMap(x -> {
			return x.getWorktimeCode().v();
		}, x -> x));

	}
	
	public WorkType getWorkType(String code) {
		return this.workTypesMap.get(code);
	}
	
	public WorkTimeSetting getWorkTimeSetting(String code) {
		return this.workTimeSettingsMap.get(code);
	}
}
