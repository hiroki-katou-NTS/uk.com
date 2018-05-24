package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCount;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 回数集計Dto
 * @author shuichu_ishida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCountDto {

	/** 回数集計NO */
	private int totalCountNo;
	
	/** 回数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "回数", layout = "A")
	private Double count;

	/** 時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "時間", layout = "B")
	private Integer time;
	
	public TotalCount toDomain(){
		return TotalCount.of(this.totalCountNo,
				this.count == null ? new AttendanceDaysMonth(0.0) : new AttendanceDaysMonth(this.count),
				this.time == null ? new AttendanceTimeMonth(0) : new AttendanceTimeMonth(this.time));
	}
	
	public static TotalCountDto from(TotalCount domain){
		TotalCountDto dto = new TotalCountDto();
		if (domain != null){
			dto.count = domain.getCount() == null ? null : domain.getCount().v();
			dto.time = domain.getTime() == null ? null : domain.getTime().v();
		}
		return dto;
	}
}
