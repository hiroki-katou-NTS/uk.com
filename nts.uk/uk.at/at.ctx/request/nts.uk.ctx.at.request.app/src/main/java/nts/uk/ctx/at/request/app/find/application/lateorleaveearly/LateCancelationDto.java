package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;

@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退取消先
public class LateCancelationDto {
//	勤務NO
	private int workNo;
//	区分
	private int lateOrEarlyClassification;
	
	public static LateCancelationDto convertDto(LateCancelation value) {
		return new LateCancelationDto(value.getWorkNo(), value.getLateOrEarlyClassification().value);
	}
}
