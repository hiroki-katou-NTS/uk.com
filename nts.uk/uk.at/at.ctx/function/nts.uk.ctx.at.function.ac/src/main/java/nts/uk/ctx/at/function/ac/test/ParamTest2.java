package nts.uk.ctx.at.function.ac.test;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ParamTest2 {
	
	String companyId;
	GeneralDate baseDate;
	List<String> listWorkplaceId;
	
}
