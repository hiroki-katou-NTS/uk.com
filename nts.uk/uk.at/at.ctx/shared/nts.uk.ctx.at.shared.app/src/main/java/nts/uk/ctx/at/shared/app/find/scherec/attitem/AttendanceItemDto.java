package nts.uk.ctx.at.shared.app.find.scherec.attitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author xuannt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceItemDto {
	//	勤怠項目ID
	private int attendanceItemId;
	//	勤怠項目の種類（スケジュール、日次、月次、週次、任意期間）
	private Integer typeOfAttendanceItem;
	//	勤怠項目.名称
	private int attendanceItemName;
}
