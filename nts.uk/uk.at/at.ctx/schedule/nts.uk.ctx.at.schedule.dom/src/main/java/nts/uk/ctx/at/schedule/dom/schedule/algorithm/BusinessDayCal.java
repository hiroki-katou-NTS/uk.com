package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDayCal {
	private FixRestTimezoneSet fixRestTimezoneSet;
	
	private TimezoneOfFixedRestTimeSet timezoneOfFixedRestTimeSet;
	
	private DiffTimeRestTimezone diffTimeRestTimezone;
	
	private List<FixRestTimezoneSet> lstFixRestTimezoneSet;
	
	private List<DiffTimeRestTimezone> lstDiffTimeRestTimezone;
}
