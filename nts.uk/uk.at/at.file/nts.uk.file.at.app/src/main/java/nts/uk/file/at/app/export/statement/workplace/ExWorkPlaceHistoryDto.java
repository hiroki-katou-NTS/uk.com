package nts.uk.file.at.app.export.statement.workplace;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.pub.generalinfo.workplace.ExWorkplaceHistItemDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExWorkPlaceHistoryDto {
	
	private String employeeId;
	
	private List<ExWorkplaceHistItemDto> workplaceItems;

}
