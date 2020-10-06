package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto_Old;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo_Old;
import nts.uk.ctx.at.shared.app.command.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

@AllArgsConstructor
@NoArgsConstructor
public class AppWorkChangeDispInfoCmd_Old {
	
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
	
	public AppWorkChangeDispInfo_Old toDomain() {
		AppWorkChangeDispInfo_Old result = new AppWorkChangeDispInfo_Old();
		result.setAppDispInfoStartupOutput(appDispInfoStartupOutput.toDomain());
		result.setAppWorkChangeSet(appWorkChangeSet.toDomain());
		result.setWorkTypeLst(workTypeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		result.setSetupType(setupType == null ? null : EnumAdaptor.valueOf(setupType, SetupType.class));
		if(predetemineTimeSetting!=null) {
			result.setPredetemineTimeSetting(new PredetemineTimeSetting(predetemineTimeSetting));
		}
		result.setWorkTypeCD(workTypeCD);
		result.setWorkTimeCD(workTimeCD);
		return result;
	}
}
