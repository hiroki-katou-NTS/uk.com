package nts.uk.ctx.at.record.app.find.monthly.root.dto.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonthlyRemarkDto implements ItemConst {
	
	/**	備考 */
	@AttendanceItemLayout(jpPropertyName = REMARK, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TEXT)
	private String remarks;	
	
	private int no;
	

	public static MonthlyRemarkDto from(RemarksMonthlyRecord domain){
		MonthlyRemarkDto dto = new MonthlyRemarkDto();
		
		dto.setRemarks(domain.getRecordRemarks() == null ? null : domain.getRecordRemarks().v());
		dto.setNo(domain.getRemarksNo());
		
		return dto;
	}
}
