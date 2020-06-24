package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedInfo;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

@Data
/** 年休使用情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedInfoDto implements ItemConst {

	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_A)
	private AnnualLeaveUsedNumberDto usedNumberBeforeGrant;
	
	/** 合計 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_B)
	private AnnualLeaveUsedNumberDto usedNumber;

	/** 時間年休使用回数 （1日2回使用した場合２回でカウント） */
	@AttendanceItemLayout(jpPropertyName = USAGE + COUNT, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.COUNT)
	private int annualLeaveUsedTimes;
	
	/** 時間年休使用日数 （1日2回使用した場合１回でカウント）  */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS, layout = LAYOUT_D)
	@AttendanceItemValue(type = ValueType.COUNT)
	private int annualLeaveUsedDayTimes;
	
	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_E)
	private AnnualLeaveUsedNumberDto usedNumberAfterGrantOpt;
	
	public static AnnualLeaveUsedInfoDto from(AnnualLeaveUsedInfo domain) {
		return new AnnualLeaveUsedInfoDto(AnnualLeaveUsedNumberDto.from(domain.getUsedNumberBeforeGrant()),
											AnnualLeaveUsedNumberDto.from(domain.getUsedNumber()), 
											domain.getAnnualLeaveUsedTimes().v(),
											domain.getAnnualLeaveUsedDayTimes().v(), 
											AnnualLeaveUsedNumberDto.from(domain.getUsedNumberAfterGrantOpt().orElse(null)));
	}
	
	public AnnualLeaveUsedInfo toDomain() {
		return new AnnualLeaveUsedInfo(usedNumberBeforeGrant == null ? new AnnualLeaveUsedNumber() : usedNumberBeforeGrant.toDomain(),
										usedNumber == null ? new AnnualLeaveUsedNumber() : usedNumber.toDomain(), 
										new UsedTimes(annualLeaveUsedTimes), new UsedTimes(annualLeaveUsedDayTimes), 
										usedNumberAfterGrantOpt == null ? Optional.empty() : Optional.of(usedNumberAfterGrantOpt.toDomain()));
	}
}
