package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JudgmentWorkTimeResult {
	// 就業時間帯変更
	private boolean workTimeChange;
	// 計算就業時間帯
	private String calcWorkTime;
}
