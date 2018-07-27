/**
 * 5:29:20 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class DPBusinessTypeControl {

	//private String businessTypeCode;
	
	private Integer attendanceItemId;
	
	private boolean useAtr;
	
	private boolean changedByOther;
	
	private boolean changedByYou;
	
}
