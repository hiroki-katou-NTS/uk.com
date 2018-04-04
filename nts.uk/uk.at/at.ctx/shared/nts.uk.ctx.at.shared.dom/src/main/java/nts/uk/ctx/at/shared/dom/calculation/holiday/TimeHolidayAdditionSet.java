package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
@Builder
// 時間休暇加算設定
public class TimeHolidayAdditionSet extends DomainObject{
	
	// 加算方法
	private TimeHolidayAddingMethod addingMethod;
	
	// 勤務区分
	private WorkClassOfTimeHolidaySet workClass;
}
