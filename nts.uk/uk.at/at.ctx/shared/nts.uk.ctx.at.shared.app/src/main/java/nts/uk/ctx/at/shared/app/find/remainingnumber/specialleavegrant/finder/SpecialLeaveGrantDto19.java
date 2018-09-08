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
public class SpecialLeaveGrantDto19 extends PeregDomainDto {

	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00749")
	private GeneralDate grantDate;

	// 期限日
	@PeregItem("IS00750")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00751")
	private int expStatus;

	// 使用状況
	@PeregItem("IS00752")
	private int registerType;

	// 付与日数
	@PeregItem("IS00754")
	private Double numberDayGrant;

	// 付与時間
	@PeregItem("IS00755")
	private Integer timeGrant;

	// 使用日数
	@PeregItem("IS00757")
	private Double numberDayUse;

	// 使用時間
	@PeregItem("IS00758")
	private Integer timeUse;

	//
	private Double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00759")
	private Double numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00760")
	private Integer timeOver;

	// 残時間
	@PeregItem("IS00762")
	private Double numberDayRemain;

	// 残日数
	@PeregItem("IS00763")
	private Integer timeRemain;

	public static SpecialLeaveGrantDto19 createFromDomain(SpecialLeaveGrantRemainingData domain) {
		SpecialLeaveGrantDto19 dto = new SpecialLeaveGrantDto19();
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
				: 0d;
		dto.numberDaysOver = domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				? domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getNumberOverDays().v()
				: 0d;
		dto.timeOver = (domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()
				&& domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver().isPresent())
						? domain.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver().get()
								.v()
						: 0;
		return dto;

	}

}
