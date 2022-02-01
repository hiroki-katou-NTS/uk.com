package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class SmileClosureTime {
	private String companyId;
	
	private int closureId;
	
	private String closureName;
	
	private  Integer endYearMonth;
	
	private GeneralDate closureDate;
	
	private Integer startYearMonth;
}
