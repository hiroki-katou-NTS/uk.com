package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
/**
 * 
 * @author nampt
 * 自動打刻セット詳細
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AutomaticStampSetDetailOutput {
	
	// 場所コード
	private String workLocationCode;

	// 出勤打刻元
	private TimeChangeMeans attendanceStamp;
	
	// 出勤反映
	private UseAtr attendanceReflectAttr;
	
	// 出退勤
	private List<TimeLeavingWork> timeLeavingWorks;
	
	// 退勤打刻元
	private TimeChangeMeans leavingStamp;
	
	// 退勤反映
	private UseAtr retirementAttr;
}
