package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonLeaveRemainingNumberDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.RealAnnualLeave;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 年休 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveDto {

	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = "使用数", layout = "A")
	private AnnualLeaveUsedNumberDto usedNumber;
	
	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = "残数", layout = "B")
	private CommonLeaveRemainingNumberDto remainingNumber;
	
	/** 残数付与前 */
	@AttendanceItemLayout(jpPropertyName = "残数付与前", layout = "C")
	private CommonLeaveRemainingNumberDto remainingNumberBeforeGrant;
	
	/** 残数付与後 */
	@AttendanceItemLayout(jpPropertyName = "残数付与後", layout = "D")
	private CommonLeaveRemainingNumberDto remainingNumberAfterGrant;
	
	/** 未消化数 */
	@AttendanceItemLayout(jpPropertyName = "未消化数", layout = "E")
	private AnnualLeaveUndigestedNumberDto undigestedNumber;
	
	public static AnnualLeaveDto from(AnnualLeave domain){
		return domain == null ? null : new AnnualLeaveDto(
				AnnualLeaveUsedNumberDto.from(domain.getUsedNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)), 
				AnnualLeaveUndigestedNumberDto.from(domain.getUndigestedNumber()));
	}
	
	public AnnualLeave toDomain(){
		return AnnualLeave.of(
						usedNumber == null ? new AnnualLeaveUsedNumber()  : usedNumber.toDomain(),
						remainingNumber == null ? new AnnualLeaveRemainingNumber()  : remainingNumber.toDomain(), 
						remainingNumberBeforeGrant == null ? new AnnualLeaveRemainingNumber() : remainingNumberBeforeGrant.toDomain(), 
						Optional.ofNullable(remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toDomain()),
						undigestedNumber == null ? null : undigestedNumber.toDomain());
	}
	
	public static AnnualLeaveDto from(RealAnnualLeave domain){
		return domain == null ? null : new AnnualLeaveDto(
				AnnualLeaveUsedNumberDto.from(domain.getUsedNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)),
				null);
	}
	
	public RealAnnualLeave toRealDomain(){
		return RealAnnualLeave.of(
						usedNumber == null ? new AnnualLeaveUsedNumber() : usedNumber.toDomain(),
						remainingNumber == null ? new AnnualLeaveRemainingNumber() : remainingNumber.toDomain(), 
						remainingNumberBeforeGrant == null ? new AnnualLeaveRemainingNumber() : remainingNumberBeforeGrant.toDomain(), 
						Optional.ofNullable(remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toDomain()));
	}
}
