/**
 * 5:29:20 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MPBusinessTypeControl {

	private String businessTypeCode;
	
	private Integer attendanceItemId;
	
	private boolean useAtr;
	
	private boolean changedByOther;
	
	private boolean changedByYou;
	
}
