package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;

@AllArgsConstructor
@NoArgsConstructor
public class BreakTimeSheetDto {
	//休憩枠NO
	public Integer breakFrameNo;
	
	//開始 - 勤怠打刻(実打刻付き)
	public Integer startTime;
	
	//終了 - 勤怠打刻(実打刻付き)
	public Integer endTime;
	
	/** 休憩時間: 勤怠時間 */
	public Integer breakTime;
	
	public static BreakTimeSheetDto fromDomain(BreakTimeSheet breakTimeSheet) {
		
		return new BreakTimeSheetDto(
				breakTimeSheet.getBreakFrameNo().v(),
				breakTimeSheet.getStartTime().v(),
				breakTimeSheet.getEndTime().v(),
				breakTimeSheet.getBreakTime().v());
	}
}
