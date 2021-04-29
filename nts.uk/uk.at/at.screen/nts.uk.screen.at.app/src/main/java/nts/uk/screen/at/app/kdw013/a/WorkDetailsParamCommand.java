package nts.uk.screen.at.app.kdw013.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto.WorkGroupDto;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@Getter
public class WorkDetailsParamCommand {
	// 応援勤務枠No: 応援勤務枠No
	private int supportFrameNo;
	
	// 時間帯: 時間帯
	
	// 作業グループ
	private WorkGroupDto workGroup;
	
	// 備考: 作業入力備考
	private String remarks;
	
	// 勤務場所: 勤務場所コード
	private String workLocationCD;

}
