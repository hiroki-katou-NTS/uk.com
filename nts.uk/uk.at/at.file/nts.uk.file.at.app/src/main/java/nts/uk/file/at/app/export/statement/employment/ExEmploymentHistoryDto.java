package nts.uk.file.at.app.export.statement.employment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.pub.generalinfo.employment.ExEmploymentHistItemDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExEmploymentHistoryDto {
	
	private String employeeId;
	
	private List<ExEmploymentHistItemDto> employmentItems;

}
