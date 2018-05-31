/**
 * 5:21:09 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class FormatMPCorrectionDto {

	private String companyId;
	
	private String businessTypeCode;
	
	private Integer attendanceItemId;
	
	private String sheetNo;
	
	private Integer order;
	
	private Integer columnWidth;
	
}
