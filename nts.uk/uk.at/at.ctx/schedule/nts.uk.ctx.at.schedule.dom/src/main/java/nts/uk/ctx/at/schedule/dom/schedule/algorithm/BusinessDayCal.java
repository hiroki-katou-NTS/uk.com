package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.Collections;
import java.util.List;

import lombok.NonNull;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

@Value
public class BusinessDayCal {
	
	@NonNull
	List<DeductionTime> timezones;
	
	public static BusinessDayCal empty() {
		return new BusinessDayCal(Collections.emptyList());
	}
}
