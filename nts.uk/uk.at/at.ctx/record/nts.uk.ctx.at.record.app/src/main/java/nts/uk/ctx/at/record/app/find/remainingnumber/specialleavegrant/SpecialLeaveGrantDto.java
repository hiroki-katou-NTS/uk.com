package nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveGrantDto {
	
	/**
	 * 
	 */
	private String specialid;

	/**
	 * 
	 */
	private String sid;
	
	/**
	 * 
	 */
	private int specialLeaCode;
	/**
	 * 付与日
	 */
	private GeneralDate grantDate;

	/**
	 * 期限日
	 */
	private GeneralDate deadlineDate;

	/**
	 * 期限切れ状態
	 */
	private int expStatus;

	/**
	 * 期限切れ状態
	 */
	private int registerType;

	/**
	 * 付与日数
	 */
	private int numberOfDayGrant;

	/**
	 * 付与時間
	 */
	private int timeGrant;

	/**
	 * 使用日数
	 */
	private double numberOfDayUse;

	/**
	 * 
	 */
	private double numberOfDayUseToLose;
	/**
	 * 使用時間
	 */
	private int timeUse;

	/**
	 * 上限超過消滅日数
	 */
	private int numberOfExceededDays;
	/**
	 * 上限超過消滅時間
	 */
	private int timeExceeded;

	/**
	 * 残日数
	 */
	private double numberOfDayRemain;

	/**
	 * 残時間
	 */
	private int timeRemain;

	public static SpecialLeaveGrantDto createFromDomain(SpecialLeaveGrantRemainingData domain) {
		SpecialLeaveGrantDto dto = new SpecialLeaveGrantDto();
		dto.specialid = domain.getSpecialId();
		dto.sid = domain.getEmployeeId();
		dto.specialLeaCode = domain.getSpecialLeaveCode().v();
		dto.grantDate = domain.getGrantDate();
		dto.deadlineDate = domain.getDeadlineDate();
		dto.expStatus = domain.getExpirationStatus().value;
		dto.registerType = domain.getRegisterType().value;
		dto.numberOfDayGrant = domain.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		dto.timeGrant = domain.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? domain.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: null;
		dto.numberOfDayRemain = domain.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		dto.timeRemain = domain.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? domain.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: null;
		dto.numberOfDayUse = domain.getDetails().getUsedNumber().getDayNumberOfUse().v();
		dto.timeUse = domain.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? domain.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: null;
		dto.numberOfDayUseToLose = domain.getDetails().getUsedNumber().getNumberOfDayUseToLose().isPresent()
				? domain.getDetails().getUsedNumber().getNumberOfDayUseToLose().get().v()
				: null;
		dto.numberOfExceededDays = domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				? domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getDayNumberOfExeeded().v()
				: null;
		dto.timeExceeded = (domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent() && domain
				.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOfExeeded().isPresent())
						? domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOfExeeded()
								.get().v()
						: null;
		return dto;

	}

}
