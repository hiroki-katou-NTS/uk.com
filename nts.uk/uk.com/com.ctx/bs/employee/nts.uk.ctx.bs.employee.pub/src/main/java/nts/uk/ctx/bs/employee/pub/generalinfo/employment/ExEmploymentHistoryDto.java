package nts.uk.ctx.bs.employee.pub.generalinfo.employment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExEmploymentHistoryDto {
	
	private String employeeId;
	
	private List<ExEmploymentHistItemDto> employmentItems;

}
