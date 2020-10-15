package nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveGrantDto{
	

	private String specialid;
	private String cid;
	private String sid;
	private int specialLeaCode;
	private GeneralDate grantDate;
	private GeneralDate deadlineDate;
	private int expStatus;
	private int registerType;
	private double numberDayGrant;
	private int timeGrant;
	private double numberDayUse;
	private int timeUse;
	private double useSavingDays;
	private double numberDaysOver;
	private int timeOver;
	private double numberDayRemain;
	private int timeRemain;

	public static SpecialLeaveGrantDto createFromDomain(SpecialLeaveGrantRemainingData domain) {
		SpecialLeaveGrantDto dto = new SpecialLeaveGrantDto();
		dto.specialid = domain.getLeaveID();
		dto.cid = domain.getCid();
		dto.sid = domain.getEmployeeId();
		dto.specialLeaCode = domain.getSpecialLeaveCode();
		dto.grantDate = domain.getGrantDate();
		dto.deadlineDate = domain.getDeadline();
		dto.expStatus = domain.getExpirationStatus().value;
		dto.registerType = domain.getRegisterType().value;
		dto.numberDayGrant = domain.getDetails().getGrantNumber().getDays().v();
		dto.timeGrant = domain.getDetails().getGrantNumber().getMinutes().isPresent()
				? domain.getDetails().getGrantNumber().getMinutes().get().v()
				: 0;
		dto.numberDayRemain = domain.getDetails().getRemainingNumber().getDays().v();
		dto.timeRemain = domain.getDetails().getRemainingNumber().getMinutes().isPresent()
				? domain.getDetails().getRemainingNumber().getMinutes().get().v()
				: 0;
		dto.numberDayUse = domain.getDetails().getUsedNumber().getDays().v();
		dto.timeUse = domain.getDetails().getUsedNumber().getMinutes().isPresent()
				? domain.getDetails().getUsedNumber().getMinutes().get().v()
				: 0;
		dto.useSavingDays = domain.getDetails().getUsedNumber().getStowageDays().isPresent()
				? domain.getDetails().getUsedNumber().getStowageDays().get().v()
				: 0d;
		dto.numberDaysOver = domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().isPresent()
				? domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().get().numberOverDays.v()
				: 0d;
		dto.timeOver = (domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().isPresent()
				&& domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().get().timeOver.isPresent())
						? domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().get().timeOver.get()
								.v()
						: 0;
		return dto;

	}

}
