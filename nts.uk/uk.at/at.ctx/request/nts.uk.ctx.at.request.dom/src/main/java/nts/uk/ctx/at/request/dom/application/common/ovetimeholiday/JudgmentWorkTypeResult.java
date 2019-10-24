package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JudgmentWorkTypeResult {
	// 勤務分類変更
	private boolean workTypeChange;
	// 計算勤務種類
	private String calcWorkType;
}
