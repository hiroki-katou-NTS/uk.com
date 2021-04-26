package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.kdw013.a.ConfirmerDto;

/**
 * 
 * @author tutt
 *
 */
public class SelectTargetEmployeeDto {
	
	// よく利用作業一覧：List<作業グループ>
	private List<WorkGroupDto> lstWorkGroupDto;
	
	// List＜確認者> 
	private List<ConfirmerDto> lstConfirmerDto;
	
	// List<日別勤怠(Work)>
	private IntegrationOfDailyDto integrationOfDailyDto;
	
	// 修正可能開始日付
	private GeneralDate modifyableStartDate;
}



