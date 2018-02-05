/**
 * 5:21:09 PM Aug 23, 2017
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
public class FormatDPCorrectionDto {

	private String companyId;
	
	private String businessTypeCode;
	
	private Integer attendanceItemId;
	
	private String sheetNo;
	
	private Integer order;
	
	private Integer columnWidth;
	
	public static FormatDPCorrectionDto from(String companyId, AuthorityFomatDailyDto x){
		return new FormatDPCorrectionDto(companyId, x.getDailyPerformanceFormatCode(),
				x.getAttendanceItemId(), x.getSheetNo().toString(), x.getDisplayOrder(),
				x.getColumnWidth().intValue());
	}
}
