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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormatDPCorrectionDto other = (FormatDPCorrectionDto) obj;
		if (attendanceItemId == null) {
			if (other.attendanceItemId != null)
				return false;
		} else if (!attendanceItemId.equals(other.attendanceItemId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (sheetNo == null) {
			if (other.sheetNo != null)
				return false;
		} else if (!sheetNo.equals(other.sheetNo))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attendanceItemId == null) ? 0 : attendanceItemId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((sheetNo == null) ? 0 : sheetNo.hashCode());
		return result;
	}
	
	
}
