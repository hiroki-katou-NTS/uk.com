package nts.uk.ctx.at.shared.dom.adapter.employee;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
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
	
	public Optional<GeneralDate> getEntryDate(){
		
		if (this.lstAffComHistItem == null || this.lstAffComHistItem.isEmpty()){
			return Optional.empty();
		}
		// Get last history
		return Optional.of(lstAffComHistItem.get(lstAffComHistItem.size()-1).getDatePeriod().start());
		
	}
}
