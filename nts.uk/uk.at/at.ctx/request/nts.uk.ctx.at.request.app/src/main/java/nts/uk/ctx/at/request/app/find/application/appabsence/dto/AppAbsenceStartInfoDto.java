package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetDto;
import nts.uk.ctx.at.request.dom.application.appabsence.service.RemainVacationInfo;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.HolidayApplicationReflectCommand;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@AllArgsConstructor
@NoArgsConstructor
public class AppAbsenceStartInfoDto {
    /**
     * 休出代休紐付け管理
     */
    public List<LeaveComDayOffManaDto> leaveComDayOffManas; 
    
    /**
     * 休暇申請の反映
     */
    public HolidayApplicationReflectCommand vacationApplicationReflect;
    
	/**
	 * 申請表示情報
	 */
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
	/**
	 * 休暇申請設定
	 */
	public HolidayApplicationSettingDto hdAppSet;
	
	/**
	 * 申請理由表示
	 */
	public DisplayReasonDto displayReason;
	
	/**
	 * 休暇残数情報
	 */
	public RemainVacationInfoDto remainVacationInfo;
	
	/**
	 * 就業時間帯表示フラグ
	 */
	public boolean workHoursDisp;
	
	/**
	 * 勤務種類一覧
	 */
	public List<WorkTypeDto> workTypeLst;
	
	/**
	 * 勤務時間帯一覧
	 */
	public List<TimeZoneUseDto> workTimeLst;
	
	/**
	 * 勤務種類マスタ未登録
	 */
	public boolean workTypeNotRegister;
	
	/**
	 * 特別休暇表示情報
	 */
	public SpecAbsenceDispInfoDto specAbsenceDispInfo;
	
	/**
	 * 選択中の勤務種類
	 */
	public String selectedWorkTypeCD;
	
	/**
	 * 選択中の就業時間帯
	 */
	public String selectedWorkTimeCD;
	
	/**
	 * 必要休暇時間
	 */
	public Integer requiredVacationTime;
	
	public List<HolidayAppTypeName> holidayAppTypeName;
	
	public static AppAbsenceStartInfoDto fromDomain(AppAbsenceStartInfoOutput absenceStartInfoOutput) {
		AppAbsenceStartInfoDto result = new AppAbsenceStartInfoDto();
		result.appDispInfoStartupOutput = AppDispInfoStartupDto.fromDomain(absenceStartInfoOutput.getAppDispInfoStartupOutput());
		result.hdAppSet = HolidayApplicationSettingDto.fromDomain(absenceStartInfoOutput.getHdAppSet());
		result.displayReason = absenceStartInfoOutput.getDisplayReason() == null ? null : DisplayReasonDto.fromDomain(absenceStartInfoOutput.getDisplayReason());
		result.remainVacationInfo = RemainVacationInfoDto.fromDomain(absenceStartInfoOutput.getRemainVacationInfo());
		result.workHoursDisp = absenceStartInfoOutput.isWorkHoursDisp();
		result.workTypeLst = CollectionUtil.isEmpty(absenceStartInfoOutput.getWorkTypeLst()) ? Collections.emptyList() : absenceStartInfoOutput.getWorkTypeLst().stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList());
		result.workTimeLst = absenceStartInfoOutput.getWorkTimeLst().stream().map(x -> TimeZoneUseDto.fromDomain(x)).collect(Collectors.toList());
		result.workTypeNotRegister = absenceStartInfoOutput.isWorkTypeNotRegister();
		result.specAbsenceDispInfo = absenceStartInfoOutput.getSpecAbsenceDispInfo().map(x -> SpecAbsenceDispInfoDto.fromDomain(x)).orElse(null);
		result.selectedWorkTypeCD = absenceStartInfoOutput.getSelectedWorkTypeCD().orElse(null);
		result.selectedWorkTimeCD = absenceStartInfoOutput.getSelectedWorkTimeCD().orElse(null);
		result.requiredVacationTime = absenceStartInfoOutput.getRequiredVacationTimeOptional().isPresent() ? absenceStartInfoOutput.getRequiredVacationTimeOptional().get().v() : null;
		result.vacationApplicationReflect = HolidayApplicationReflectCommand.fromDomain(absenceStartInfoOutput.getVacationAppReflect());
		return result;
	}
	
	public AppAbsenceStartInfoOutput toDomain(String companyId) {
		return new AppAbsenceStartInfoOutput(
				appDispInfoStartupOutput.toDomain(), 
				vacationApplicationReflect.toDomain(companyId),
				hdAppSet.toDomain(companyId), 
				displayReason == null ? null : displayReason.toDomain(), 
				remainVacationInfo.toDomain(), 
				workHoursDisp, 
				CollectionUtil.isEmpty(workTypeLst) ? Collections.emptyList() : workTypeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(workTimeLst) ? Collections.emptyList() : workTimeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				workTypeNotRegister, 
				specAbsenceDispInfo == null ? Optional.empty() : Optional.of(specAbsenceDispInfo.toDomain()), 
				Optional.ofNullable(selectedWorkTypeCD), 
				Optional.ofNullable(selectedWorkTimeCD),
				requiredVacationTime == null ? Optional.empty() : Optional.of(new AttendanceTime(requiredVacationTime)));
	}
}
