package nts.uk.screen.at.app.kdw006.j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyAttendanceItemDto {

	/* 会社ID */
	private String companyId;

	/* 勤怠項目ID */
	private int attendanceItemId;

	/* 勤怠項目名称 */
	private String attendanceName;

	/* 表示番号 */
	private int displayNumber;

	/* ユーザーが値を変更できる */
	private int userCanUpdateAtr;

	/* 勤怠項目属性 */
	private int dailyAttendanceAtr;

	/* 名称の改行位置 */
	private int nameLineFeedPosition;

	/* マスタの種類 */
	private Integer masterType;

	/* 怠項目のPrimitiveValue */
	private Integer primitiveValue;

	/* 表示名称 */
	private String displayName;

}
