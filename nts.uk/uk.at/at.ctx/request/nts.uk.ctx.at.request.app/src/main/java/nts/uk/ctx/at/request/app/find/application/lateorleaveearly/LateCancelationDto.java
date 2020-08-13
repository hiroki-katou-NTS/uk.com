package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;

@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退取消先
public class LateCancelationDto {
//	勤務NO
	private int workNo;
//	区分
	private int lateOrEarlyClassification;
	
	public static LateCancelationDto fromDomain(LateCancelation value) {
		return new LateCancelationDto(value.getWorkNo(), value.getLateOrEarlyClassification().value);
	}
	public LateCancelation toDomain() {
		return new LateCancelation(
				workNo,
				EnumAdaptor.valueOf(lateOrEarlyClassification, LateOrEarlyAtr.class));
	}
}
