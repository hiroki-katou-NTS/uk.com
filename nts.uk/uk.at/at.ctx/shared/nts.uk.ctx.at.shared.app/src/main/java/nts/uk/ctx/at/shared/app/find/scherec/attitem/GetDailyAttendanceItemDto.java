package nts.uk.ctx.at.shared.app.find.scherec.attitem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapterDto;
/**
 * 
 * @author xuannt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDailyAttendanceItemDto {
	//	List<日次の勤怠項目>
	private List<AttendanceItemDto> dailyAttendanceList;
	//	List<勤怠項目と枠の紐付け>
	private List<FrameNoAdapterDto> dailyAttendanceLinkingList;
	
}
