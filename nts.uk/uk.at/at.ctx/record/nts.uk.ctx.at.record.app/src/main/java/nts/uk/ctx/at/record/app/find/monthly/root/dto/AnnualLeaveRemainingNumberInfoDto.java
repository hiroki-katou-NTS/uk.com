package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonLeaveRemainingNumberDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 年休残数情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveRemainingNumberInfoDto implements ItemConst {

	/** 合計 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private CommonLeaveRemainingNumberDto remainingNumber;
	
	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private CommonLeaveRemainingNumberDto before;
	
	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = AFTER, layout = LAYOUT_C)
	private CommonLeaveRemainingNumberDto after;
	
	public static AnnualLeaveRemainingNumberInfoDto from(AnnualLeaveRemainingNumberInfo domain) {
		return new AnnualLeaveRemainingNumberInfoDto(CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()),
														CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()), 
														CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrantOpt().orElse(null)));
	}
	
	public AnnualLeaveRemainingNumberInfo toDomain() {
		return new AnnualLeaveRemainingNumberInfo(remainingNumber == null ? new AnnualLeaveRemainingNumber() : remainingNumber.toDomain(),
													before == null ? new AnnualLeaveRemainingNumber() : before.toDomain(), 
													after == null ? Optional.empty() : Optional.of(after.toDomain()));
	}
}
