package nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

@Getter
@Setter
@NoArgsConstructor
public class BreakTimeZoneSharedOutPut {
	
	private List<DeductionTime> lstTimezone;

}
