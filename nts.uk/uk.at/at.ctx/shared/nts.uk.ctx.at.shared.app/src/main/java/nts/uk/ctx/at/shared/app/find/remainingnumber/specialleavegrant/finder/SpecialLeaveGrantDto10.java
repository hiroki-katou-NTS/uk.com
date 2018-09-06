package nts.uk.ctx.at.shared.app.find.remainingnumber.specialleavegrant.finder;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
public class SpecialLeaveGrantDto10 extends PeregDomainDto {

	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00544")
	private GeneralDate grantDate;

	// 期限日
	@PeregItem("IS00545")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00546")
	private int expStatus;

	// 使用状況
	@PeregItem("IS00547")
	private int registerType;

	// 付与日数
	@PeregItem("IS00549")
	private double numberDayGrant;

	// 付与時間
	@PeregItem("IS00550")
	private int timeGrant;

	// 使用日数
	@PeregItem("IS00552")
	private double numberDayUse;

	// 使用時間
	@PeregItem("IS00553")
	private int timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00554")
	private double numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00555")
	private int timeOver;

	// 残時間
	@PeregItem("IS00557")
	private double numberDayRemain;

	// 残日数
	@PeregItem("IS00558")
	private int timeRemain;



	public static SpecialLeaveGrantDto10 createFromDomain(SpecialLeaveGrantRemainingData domain) {
		SpecialLeaveGrantDto10 dto = new SpecialLeaveGrantDto10();
		dto.specialid = domain.getSpecialId();
		dto.cid = domain.getCId();
		dto.sid = domain.getEmployeeId();
		dto.specialLeaCode = domain.getSpecialLeaveCode().v();
		dto.grantDate = domain.getGrantDate();
		dto.deadlineDate = domain.getDeadlineDate();
		dto.expStatus = domain.getExpirationStatus().value;
		dto.registerType = domain.getRegisterType().value;
		dto.numberDayGrant = domain.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		dto.timeGrant = domain.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? domain.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: 0;
		dto.numberDayRemain = domain.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		dto.timeRemain = domain.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? domain.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: 0;
		dto.numberDayUse = domain.getDetails().getUsedNumber().getDayNumberOfUse().v();
		dto.timeUse = domain.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? domain.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: 0;
		dto.useSavingDays = domain.getDetails().getUsedNumber().getUseSavingDays().isPresent()
				? domain.getDetails().getUsedNumber().getUseSavingDays().get().v()
				: 0;
		dto.numberDaysOver = domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				? domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getNumberOverDays().v()
				: 0;
		dto.timeOver = (domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				&& domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver().isPresent())
						? domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver().get()
								.v()
						: 0;
		return dto;

	}

}
