package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;

import nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto.WorkTimeInformationDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.BreakTimeSheetDto;

/**
 * 
 * @author tutt
 *
 */
public class ActualContentDto {
	//休憩リスト
	private List<BreakTimeSheetDto> breakTimeSheets;
	
	//休憩時間
	private Integer breakHours;
		
	//終了時刻
	private WorkTimeInformationDto end;
	
	//総労働時間
	private Integer totalWorkingHours;
	
	//開始時刻
	private WorkTimeInformationDto start;
	
}
