package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDayCal {
	List<DeductionTime> timezones;
}
