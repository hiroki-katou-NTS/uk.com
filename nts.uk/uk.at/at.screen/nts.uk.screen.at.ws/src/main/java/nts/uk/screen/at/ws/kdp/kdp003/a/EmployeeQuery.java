package nts.uk.screen.at.ws.kdp.kdp003.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeQuery {
	private String companyId;
	
	private String workplaceId;
	
	private GeneralDate baseDate;
}
