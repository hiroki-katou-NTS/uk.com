package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Setter
@Getter
@NoArgsConstructor
public class OutputScreenListOfStampDto {
	 String companyCode;
	 String companyName;
	 GeneralDate startDate;
	 GeneralDate endDate;
	 boolean existAuthEmpl;
}
