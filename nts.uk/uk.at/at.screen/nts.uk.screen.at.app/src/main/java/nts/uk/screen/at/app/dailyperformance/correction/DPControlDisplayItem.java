/**
 * 5:42:15 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
public class DPControlDisplayItem {
	private List<DPAttendanceItem> attendanceItem;
	private List<DPAttendanceItemControl> attendanceItemControl;
	private List<FormatDPCorrectionDto> lstFormat;
}
