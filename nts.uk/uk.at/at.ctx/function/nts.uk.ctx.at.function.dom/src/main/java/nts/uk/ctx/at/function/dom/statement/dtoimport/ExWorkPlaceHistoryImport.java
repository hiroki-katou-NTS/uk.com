package nts.uk.ctx.at.function.dom.statement.dtoimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExWorkPlaceHistoryImport {
	
	private String employeeId;
	
	private List<ExWorkplaceHistItemImport> workplaceItems;

}
