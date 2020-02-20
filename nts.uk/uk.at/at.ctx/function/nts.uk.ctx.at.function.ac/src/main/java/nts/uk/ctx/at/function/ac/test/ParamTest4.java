package nts.uk.ctx.at.function.ac.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ParamTest4 {
	
	String companyId;
	GeneralDate baseDate;
	String parentWorkplaceId;
	
}
