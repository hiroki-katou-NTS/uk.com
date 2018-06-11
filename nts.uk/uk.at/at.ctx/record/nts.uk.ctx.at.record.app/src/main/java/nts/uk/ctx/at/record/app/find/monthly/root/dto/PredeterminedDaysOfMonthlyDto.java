package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.PredeterminedDaysOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の所定日数 */
public class PredeterminedDaysOfMonthlyDto implements ItemConst {

	/** 所定日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private Double predeterminedDays;

	/** 所定日数付与前: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private Double predeterminedDaysBeforeGrant;

	/** 所定日数付与後: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = AFTER, layout = LAYOUT_C)
	private Double predeterminedDaysAfterGrant;

	public PredeterminedDaysOfMonthly toDomain() {
		return PredeterminedDaysOfMonthly.of(
						predeterminedDays == null ? null : new AttendanceDaysMonth(predeterminedDays));//,
//						predeterminedDaysBeforeGrant == null ? null : new AttendanceDaysMonth(predeterminedDaysBeforeGrant), 
//						predeterminedDaysAfterGrant == null ? null : new AttendanceDaysMonth(predeterminedDaysAfterGrant));
	}
	
	public static PredeterminedDaysOfMonthlyDto from(PredeterminedDaysOfMonthly domain) {
		PredeterminedDaysOfMonthlyDto dto = new PredeterminedDaysOfMonthlyDto();
		if(domain != null) {
			dto.setPredeterminedDays(domain.getPredeterminedDays() == null 
					? null : domain.getPredeterminedDays().v());
//			dto.setPredeterminedDaysAfterGrant(domain.getPredeterminedDaysAfterGrant() == null 
//					? null : domain.getPredeterminedDaysAfterGrant().v());
//			dto.setPredeterminedDaysBeforeGrant(domain.getPredeterminedDaysBeforeGrant() == null 
//					? null : domain.getPredeterminedDaysBeforeGrant().v());
		}
		return dto;
	}
}
