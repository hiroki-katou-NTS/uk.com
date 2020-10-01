package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCount;

/**
 * 回数集計Dto
 * @author shuichu_ishida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCountDto implements ItemConst {

	/** 回数集計NO */
	private int no;
	
	/** 回数 */
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	private double count;

	/** 時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int time;
	
	
	public TotalCount toDomain(){
		return TotalCount.of(this.no, new AttendanceDaysMonth(this.count), new AttendanceTimeMonth(this.time));
	}
	
	public static TotalCountDto from(TotalCount domain){
		TotalCountDto dto = new TotalCountDto();
		if (domain != null){
			dto.no = domain.getTotalCountNo();
			dto.count = domain.getCount() == null ? 0 : domain.getCount().v();
			dto.time = domain.getTime() == null ? 0 : domain.getTime().v();
		}
		return dto;
	}
}
