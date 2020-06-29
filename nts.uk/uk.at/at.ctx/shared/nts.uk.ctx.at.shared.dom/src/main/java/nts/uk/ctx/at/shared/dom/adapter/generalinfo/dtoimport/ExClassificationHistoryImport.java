package nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExClassificationHistoryImport {
	
	private String employeeId;
	
	private List<ExClassificationHistItemImport> classificationItems;

}
