package nts.uk.file.at.app.export.statement.jobtitle;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.pub.generalinfo.jobtitle.ExJobTitleHistItemDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExJobTitleHistoryDto {
	
	private String employeeId;
	
	private List<ExJobTitleHistItemDto> jobTitleItems;

}
