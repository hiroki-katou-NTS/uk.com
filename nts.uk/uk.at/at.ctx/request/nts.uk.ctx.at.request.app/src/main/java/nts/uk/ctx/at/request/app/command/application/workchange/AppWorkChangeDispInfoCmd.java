package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.ReflectWorkChangeAppDto;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.shared.app.command.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeDispInfoCmd {
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
	
	public AppWorkChangeDispInfo toDomain() {
		AppWorkChangeDispInfo appWorkChangeDispInfo = new AppWorkChangeDispInfo();
		if (predetemineTimeSetting != null) {
			appWorkChangeDispInfo.setPredetemineTimeSetting(Optional.of(new PredetemineTimeSetting(predetemineTimeSetting)));
		} else {
			appWorkChangeDispInfo.setPredetemineTimeSetting(Optional.empty());
		}
		appWorkChangeDispInfo.setAppDispInfoStartupOutput(appDispInfoStartupOutput.toDomain());
		appWorkChangeDispInfo.setAppWorkChangeSet(appWorkChangeSet.toDomain());
		appWorkChangeDispInfo.setWorkTypeLst(workTypeLst.stream().map(item -> item.toDomain()).collect(Collectors.toList()));
		if (setupType !=null) {
			appWorkChangeDispInfo.setSetupType(Optional.of(EnumAdaptor.valueOf(setupType, SetupType.class)));			
		}else {
			appWorkChangeDispInfo.setSetupType(Optional.empty());
		}
		appWorkChangeDispInfo.setWorkTypeCD(Optional.ofNullable(workTypeCD));
		appWorkChangeDispInfo.setWorkTimeCD(Optional.ofNullable(workTimeCD));
		appWorkChangeDispInfo.setReflectWorkChangeApp(reflectWorkChangeAppDto.toDomain());
		return appWorkChangeDispInfo;
	}
}
