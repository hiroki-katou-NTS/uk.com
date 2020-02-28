package nts.uk.ctx.at.function.ac.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkPlaceConfigDto {
	String companyId;
	String historyId;
	GeneralDate startDate;
	GeneralDate endDate;
}
