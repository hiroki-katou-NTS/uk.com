package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.BreakDownTimeDayDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetermineTimeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeDispInfoDto {
	/**
	 * 申請表示情報
	 */
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
	/**
	 * 勤務変更申請設定
	 */
	public AppWorkChangeSetDto appWorkChangeSet;
	
	/**
	 * 勤務種類リスト
	 */
	public List<WorkTypeDto> workTypeLst;
	
	/**
	 * 就業時間帯の必須区分
	 */
	public Integer setupType;
	
	/**
	 * 所定時間設定
	 */
	public PredetemineTimeSettingDto predetemineTimeSetting;
	
	/**
	 * 選択中の勤務種類
	 */
	public String workTypeCD;
	
	/**
	 * 選択中の就業時間帯
	 */
	public String workTimeCD;
	
//	勤務変更申請の反映
	public ReflectWorkChangeAppDto reflectWorkChangeAppDto;
	
	
	
	public static AppWorkChangeDispInfoDto fromDomain(AppWorkChangeDispInfo appWorkChangeDispInfo) {
		AppWorkChangeDispInfoDto result = new AppWorkChangeDispInfoDto();
		result.appDispInfoStartupOutput = AppDispInfoStartupDto.fromDomain(appWorkChangeDispInfo.getAppDispInfoStartupOutput());
		result.appWorkChangeSet = AppWorkChangeSetDto.fromDomain(appWorkChangeDispInfo.getAppWorkChangeSet());
		result.workTypeLst = appWorkChangeDispInfo.getWorkTypeLst().stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList());
		result.setupType = appWorkChangeDispInfo.getSetupType().isPresent() ? appWorkChangeDispInfo.getSetupType().get().value : null;
		if(appWorkChangeDispInfo.getPredetemineTimeSetting().isPresent()) {
			PredetemineTimeSettingDto predetemineTimeSettingDto = new PredetemineTimeSettingDto();
			appWorkChangeDispInfo.getPredetemineTimeSetting().get().saveToMemento(predetemineTimeSettingDto);
			result.predetemineTimeSetting = predetemineTimeSettingDto;
		}
		if (appWorkChangeDispInfo.getWorkTypeCD().isPresent()) {
			result.workTypeCD = appWorkChangeDispInfo.getWorkTypeCD().get();			
		}
		if (appWorkChangeDispInfo.getWorkTimeCD().isPresent()) {
			result.workTimeCD = appWorkChangeDispInfo.getWorkTimeCD().get();			
		}
		result.reflectWorkChangeAppDto = ReflectWorkChangeAppDto.fromDomain(appWorkChangeDispInfo.getReflectWorkChangeApp());
		return result;
	}
	public AppWorkChangeDispInfo toDomain() {
		AppWorkChangeDispInfo appWorkChangeDispInfo = new AppWorkChangeDispInfo();
		PredetemineTimeSetting prescribedTimeSetting = null;
		if (predetemineTimeSetting != null) {
			PredetermineTimeDto predetermineTimeDto = predetemineTimeSetting.getPredTime();
			BreakDownTimeDayDto addTime = predetermineTimeDto.addTime;
			BreakDownTimeDayDto predTime = predetermineTimeDto.predTime;
			PredetermineTime predetermineTime = new PredetermineTime(new BreakDownTimeDay(addTime.getOneDay(), addTime.morning, addTime.afternoon), new BreakDownTimeDay(predTime.getOneDay(), predTime.morning, predTime.afternoon));
			
//		predetemineTimeSetting.getPredTime().getAddTime();
//		predetermineTime.saveToMemento();
			PrescribedTimezoneSetting prescribedTimezoneSetting = new PrescribedTimezoneSetting(
					new TimeWithDayAttr(predetemineTimeSetting.prescribedTimezoneSetting.morningEndTime),
					new TimeWithDayAttr(predetemineTimeSetting.prescribedTimezoneSetting.afternoonStartTime),
					predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone.stream().map(item -> new TimezoneUse(
							new TimeWithDayAttr(item.start),
							new TimeWithDayAttr(item.end),
							EnumAdaptor.valueOf(item.useAtr ? 1 : 0, UseSetting.class),
							item.workNo)).collect(Collectors.toList())
					);
			
			prescribedTimezoneSetting.saveToMemento(predetemineTimeSetting.prescribedTimezoneSetting);
			
			prescribedTimeSetting = new PredetemineTimeSetting(
					predetemineTimeSetting.companyId,
					new AttendanceTime(predetemineTimeSetting.rangeTimeDay),
					new WorkTimeCode(predetemineTimeSetting.workTimeCode),
					predetermineTime,
					predetemineTimeSetting.nightShift,
					prescribedTimezoneSetting,
					new TimeWithDayAttr(predetemineTimeSetting.startDateClock),
					predetemineTimeSetting.predetermine);
			
		}
		appWorkChangeDispInfo.setAppDispInfoStartupOutput(appDispInfoStartupOutput.toDomain());
		appWorkChangeDispInfo.setAppWorkChangeSet(appWorkChangeSet.toDomain());
		appWorkChangeDispInfo.setWorkTypeLst(workTypeLst.stream().map(item -> item.toDomain()).collect(Collectors.toList()));
		if (setupType !=null) {
			appWorkChangeDispInfo.setSetupType(Optional.of(EnumAdaptor.valueOf(setupType, SetupType.class)));			
		}else {
			appWorkChangeDispInfo.setSetupType(Optional.empty());
		}
		appWorkChangeDispInfo.setPredetemineTimeSetting(Optional.ofNullable(prescribedTimeSetting));
		appWorkChangeDispInfo.setWorkTypeCD(Optional.ofNullable(workTypeCD));
		appWorkChangeDispInfo.setWorkTimeCD(Optional.ofNullable(workTimeCD));
		appWorkChangeDispInfo.setReflectWorkChangeApp(reflectWorkChangeAppDto.toDomain());
		return appWorkChangeDispInfo;
	}
	 
}
