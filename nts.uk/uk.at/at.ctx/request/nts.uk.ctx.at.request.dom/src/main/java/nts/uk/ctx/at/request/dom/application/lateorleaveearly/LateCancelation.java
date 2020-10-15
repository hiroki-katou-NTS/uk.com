package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退取消先
public class LateCancelation {
//	勤務NO
	private int workNo;
//	区分
	private LateOrEarlyAtr lateOrEarlyClassification; 
}
