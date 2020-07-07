package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto_Old;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo_Old;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@NoArgsConstructor
@AllArgsConstructor
public class AppWorkChangeDispInfoDto_Old {
	
	/**
	 * 申請表示情報
	 */
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
	/**
	 * 勤務変更申請設定
	 */
	public AppWorkChangeSetDto_Old appWorkChangeSet;
	
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
	
	public static AppWorkChangeDispInfoDto_Old fromDomain(AppWorkChangeDispInfo_Old appWorkChangeDispInfo) {
		AppWorkChangeDispInfoDto_Old result = new AppWorkChangeDispInfoDto_Old();
		result.appDispInfoStartupOutput = AppDispInfoStartupDto.fromDomain(appWorkChangeDispInfo.getAppDispInfoStartupOutput());
		result.appWorkChangeSet = AppWorkChangeSetDto_Old.fromDomain(appWorkChangeDispInfo.getAppWorkChangeSet());
		result.workTypeLst = appWorkChangeDispInfo.getWorkTypeLst().stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList());
		result.setupType = appWorkChangeDispInfo.getSetupType() == null ? null : appWorkChangeDispInfo.getSetupType().value;
		if(appWorkChangeDispInfo.getPredetemineTimeSetting()!=null) {
			PredetemineTimeSettingDto predetemineTimeSettingDto = new PredetemineTimeSettingDto();
			appWorkChangeDispInfo.getPredetemineTimeSetting().saveToMemento(predetemineTimeSettingDto);
			result.predetemineTimeSetting = predetemineTimeSettingDto;
		}
		result.workTypeCD = appWorkChangeDispInfo.getWorkTypeCD();
		result.workTimeCD = appWorkChangeDispInfo.getWorkTimeCD();
		return result;
	}
	
}
