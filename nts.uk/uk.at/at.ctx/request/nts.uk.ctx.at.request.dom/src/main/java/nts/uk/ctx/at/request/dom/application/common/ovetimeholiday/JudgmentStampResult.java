package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JudgmentStampResult {
	// 打刻漏れフラグ 
	private boolean missStamp;
	// 退勤打刻補正 
	private boolean stampLeaveChange;
	// 計算退勤時刻 
	private Integer calcLeaveStamp;
}
