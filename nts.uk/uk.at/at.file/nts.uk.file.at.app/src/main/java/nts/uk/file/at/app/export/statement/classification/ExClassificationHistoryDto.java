package nts.uk.file.at.app.export.statement.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.pub.generalinfo.classification.ExClassificationHistItemDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExClassificationHistoryDto {
	
	private String employeeId;
	
	private List<ExClassificationHistItemDto> classificationItems;

}
