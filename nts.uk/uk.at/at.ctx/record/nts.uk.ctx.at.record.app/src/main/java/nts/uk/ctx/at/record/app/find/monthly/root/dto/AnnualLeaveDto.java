package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumberInfo;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedInfo;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 年休 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveDto implements ItemConst {

	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = USAGE + NUMBER, layout = LAYOUT_A)
	private AnnualLeaveUsedInfoDto usedNumber;
	
	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_B)
	private AnnualLeaveRemainingNumberInfoDto remainingNumber;
	
	public static AnnualLeaveDto from(AnnualLeave domain){
		return domain == null ? null : new AnnualLeaveDto(
				AnnualLeaveUsedInfoDto.from(domain.getUsedNumberInfo()), 
				AnnualLeaveRemainingNumberInfoDto.from(domain.getRemainingNumberInfo()));
	}
	
	public AnnualLeave toDomain(){
		return AnnualLeave.of(
						usedNumber == null ? new AnnualLeaveUsedInfo()  : usedNumber.toDomain(),
						remainingNumber == null ? new AnnualLeaveRemainingNumberInfo()  : remainingNumber.toDomain()
							);
	}
}
