package nts.uk.ctx.at.shared.app.find.employmentrules.workclosuredate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OutputCalculateMonthly {

	private GeneralDate startMonth;
	
	private GeneralDate endMonth;
}
