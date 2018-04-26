package nts.uk.ctx.at.record.dom.adapter.company;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AffCompanyHistImport {
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	// List Affiliated company history item
	private List<AffComHistItemImport> lstAffComHistItem;
}
