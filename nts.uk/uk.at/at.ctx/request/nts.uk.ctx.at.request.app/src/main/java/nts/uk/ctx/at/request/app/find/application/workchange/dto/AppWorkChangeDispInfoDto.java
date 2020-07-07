package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto_Old;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
@AllArgsConstructor
@NoArgsConstructor
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
}
