package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonLeaveRemainingNumberDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUndigestedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.RealAnnualLeave;

@Data
/** 年休 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveDto implements ItemConst, AttendanceItemDataGate {

	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = USAGE + NUMBER, layout = LAYOUT_A)
	private DayTimeUsedNumberDto usedNumber;
	
	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_B)
	private CommonLeaveRemainingNumberDto remainingNumber;
	
	/** 残数付与前 */
	@AttendanceItemLayout(jpPropertyName = REMAIN + GRANT + BEFORE, layout = LAYOUT_C)
	private CommonLeaveRemainingNumberDto remainingNumberBeforeGrant;
	
	/** 残数付与後 */
	@AttendanceItemLayout(jpPropertyName = REMAIN + GRANT + AFTER, layout = LAYOUT_D)
	private CommonLeaveRemainingNumberDto remainingNumberAfterGrant;
	
	/** 未消化数 */
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_E)
	private AnnualLeaveUndigestedNumberDto undigestedNumber;
	
	public static AnnualLeaveDto from(AnnualLeave domain){
		return domain == null ? null : new AnnualLeaveDto(
				DayTimeUsedNumberDto.from(domain.getUsedNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)), 
				AnnualLeaveUndigestedNumberDto.from(domain.getUndigestedNumber()));
	}
	
	public AnnualLeave toDomain(){
		return AnnualLeave.of(
						usedNumber == null ? new AnnualLeaveUsedNumber()  : usedNumber.toAnnual(),
						remainingNumber == null ? new AnnualLeaveRemainingNumber()  : remainingNumber.toDomain(), 
						remainingNumberBeforeGrant == null ? new AnnualLeaveRemainingNumber() : remainingNumberBeforeGrant.toDomain(), 
						Optional.ofNullable(remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toDomain()),
						undigestedNumber == null ? new AnnualLeaveUndigestedNumber() : undigestedNumber.toDomain());
	}
	
	public static AnnualLeaveDto from(RealAnnualLeave domain){
		return domain == null ? null : new AnnualLeaveDto(
				DayTimeUsedNumberDto.from(domain.getUsedNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()), 
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
				CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)),
				null);
	}
	
	public RealAnnualLeave toRealDomain(){
		return RealAnnualLeave.of(
						usedNumber == null ? new AnnualLeaveUsedNumber() : usedNumber.toAnnual(),
						remainingNumber == null ? new AnnualLeaveRemainingNumber() : remainingNumber.toDomain(), 
						remainingNumberBeforeGrant == null ? new AnnualLeaveRemainingNumber() : remainingNumberBeforeGrant.toDomain(), 
						Optional.ofNullable(remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toDomain()));
	}


	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case (USAGE + NUMBER):
			return new DayTimeUsedNumberDto();
		case (REMAIN):
		case (REMAIN + GRANT + BEFORE):
		case (REMAIN + GRANT + AFTER):
			return new CommonLeaveRemainingNumberDto();
		case (NOT_DIGESTION):
			return new AnnualLeaveUndigestedNumberDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (USAGE + NUMBER):
			return Optional.ofNullable(usedNumber);
		case (REMAIN):
			return Optional.ofNullable(remainingNumber);
		case (REMAIN + GRANT + BEFORE):
			return Optional.ofNullable(remainingNumberBeforeGrant);
		case (REMAIN + GRANT + AFTER):
			return Optional.ofNullable(remainingNumberAfterGrant);
		case (NOT_DIGESTION):
			return Optional.ofNullable(undigestedNumber);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (USAGE + NUMBER):
			usedNumber = (DayTimeUsedNumberDto) value; break;
		case (REMAIN):
			remainingNumber = (CommonLeaveRemainingNumberDto) value; break;
		case (REMAIN + GRANT + BEFORE):
			remainingNumberBeforeGrant = (CommonLeaveRemainingNumberDto) value; break;
		case (REMAIN + GRANT + AFTER):
			remainingNumberAfterGrant = (CommonLeaveRemainingNumberDto) value; break;
		case (NOT_DIGESTION):
			undigestedNumber = (AnnualLeaveUndigestedNumberDto) value; break;
		default:
			break;
		}
	}

}
