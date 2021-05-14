package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CareType;

@AllArgsConstructor
@Data
public class CheckCareResult {
	// true/false（介護看護休暇設定とNOが一致したかどうか）
	private boolean isCare;

	// 介護看護区分
	private CareType careType;
}
