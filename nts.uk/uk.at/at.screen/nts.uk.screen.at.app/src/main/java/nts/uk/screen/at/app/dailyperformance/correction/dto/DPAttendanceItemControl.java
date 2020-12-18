/**
 * 5:45:13 PM Aug 23, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class DPAttendanceItemControl {

	private Integer attendanceItemId;

	// 時間項目の入力単位
	// 0:1分
	// 1:5分
	// 2:10分
	// 3:15分
	// 4:30分
	// 5:60分
	private BigDecimal timeInputUnit;

	private String headerBackgroundColor;

	private Integer lineBreakPosition;
}