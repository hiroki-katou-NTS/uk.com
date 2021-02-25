package nts.uk.ctx.at.shared.dom.application.lateleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退取消先
public class LateCancelationShare {

	// 勤務NO
	private int workNo;

	// 区分
	private LateOrEarlyAtrShare lateOrEarlyClassification;
}
