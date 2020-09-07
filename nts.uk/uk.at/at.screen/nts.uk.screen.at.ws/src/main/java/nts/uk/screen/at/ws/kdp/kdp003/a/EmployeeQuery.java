package nts.uk.screen.at.ws.kdp.kdp003.a;

import java.util.List;

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
	
	private List<String> workplaceIds;
	
	private GeneralDate baseDate;
}
