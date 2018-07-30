package nts.uk.ctx.at.shared.dom.adapter.employee;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AffCompanyHistSharedImport {
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	// List Affiliated company history item
	private List<AffComHistItemShareImport> lstAffComHistItem;
}
