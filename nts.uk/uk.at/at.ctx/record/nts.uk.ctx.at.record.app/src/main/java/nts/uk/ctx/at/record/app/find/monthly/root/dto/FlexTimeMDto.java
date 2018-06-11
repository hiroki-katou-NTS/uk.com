package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

@Data
/** フレックス時間 */
@NoArgsConstructor
@AllArgsConstructor
public class FlexTimeMDto implements ItemConst {

	/** フレックス時間: 計算付き月間時間(マイナス有り) */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto flexTime;

	/** 事前フレックス時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private Integer beforeFlexTime;

	/** 法定外フレックス時間: 勤怠月間時間(マイナス有り) */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = ILLEGAL, layout = LAYOUT_C)
	private Integer illegalFlexTime;

	/** 法定内フレックス時間: 勤怠月間時間(マイナス有り) */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = LEGAL, layout = LAYOUT_D)
	private Integer legalFlexTime;

	public FlexTime toDomain() {
		return FlexTime.of(flexTime == null ? null : flexTime.toDomainWithMinus(),
				beforeFlexTime == null ? null : new AttendanceTimeMonth(beforeFlexTime),
				legalFlexTime == null ? null : new AttendanceTimeMonthWithMinus(legalFlexTime),
				illegalFlexTime == null ? null : new AttendanceTimeMonthWithMinus(illegalFlexTime));
	}
	
	public static FlexTimeMDto from(FlexTime domain) {
		FlexTimeMDto dto = new FlexTimeMDto();
		if(domain != null) {
			dto.setBeforeFlexTime(domain.getBeforeFlexTime() == null ? null : domain.getBeforeFlexTime().valueAsMinutes());
			dto.setFlexTime(TimeMonthWithCalculationDto.from(domain.getFlexTime()));
			dto.setIllegalFlexTime(domain.getIllegalFlexTime() == null ? null : domain.getIllegalFlexTime().valueAsMinutes());
			dto.setLegalFlexTime(domain.getLegalFlexTime() == null ? null : domain.getLegalFlexTime().valueAsMinutes());
		}
		return dto;
	}
}
