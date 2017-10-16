/**
 * 5:44:29 PM Aug 23, 2017
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
public class DPAttendanceItem {
	private Integer id;
	private String name;
	private Integer displayNumber;
	private boolean userCanSet;
	private Integer lineBreakPosition;
	// 勤怠項目属性
	// 0: 時間
	// 1: 時刻
	// 2: 回数
	// 3: 区分
	// 4: ｺｰﾄﾞ
	// 5: 金額
	private Integer attendanceAtr;
}
