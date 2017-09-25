package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeriodCurrentMonth {

	GeneralDate startDate;
	GeneralDate endDate;
	
}
