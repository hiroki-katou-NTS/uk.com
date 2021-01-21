package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HoldingQualificationOutput {
	
	private String sid;
	
	private List<EligibilityQualification> eligibilityQualifications;
	
}
