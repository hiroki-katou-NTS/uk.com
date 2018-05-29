package nts.uk.ctx.bs.employee.pub.generalinfo.jobtitle;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExJobTitleHistoryDto {
	
	private String employeeId;
	
	private List<ExJobTitleHistItemDto> jobTitleItems;

}
