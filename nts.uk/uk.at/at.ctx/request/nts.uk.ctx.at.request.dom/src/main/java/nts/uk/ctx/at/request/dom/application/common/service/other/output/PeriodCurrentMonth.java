package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeriodCurrentMonth {
	ClosureId closureId;
	GeneralDate startDate;
	GeneralDate endDate;
	
}
