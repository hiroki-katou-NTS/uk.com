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
	
	public static SpecialLeaveGrantDto19 createFromDomain(Object[] domain) {
		SpecialLeaveGrantDto19 dto = new SpecialLeaveGrantDto19();
		dto.specialid = domain[0].toString();
		dto.cid = domain[1].toString();
		dto.sid = domain[2].toString();
		dto.specialLeaCode = (Integer)domain[3];
		dto.grantDate = (GeneralDate) domain[4];
		dto.deadlineDate = (GeneralDate) domain[5];
		dto.expStatus = (Integer) domain[6];
		dto.registerType = (Integer) domain[7];
		dto.numberDayGrant = (Double) domain[8];
		dto.timeGrant = domain[9] == null? 0: (Integer) domain[9];
		dto.numberDayRemain = (Double) domain[10];
		dto.timeRemain = domain[11] == null? 0: (Integer) domain[11];
		dto.numberDayUse = (Double) domain[12];
		dto.timeUse = domain[13] == null? 0: (Integer) domain[13];
		dto.useSavingDays = domain[14] == null? 0d : (Double) domain[14];
		dto.numberDaysOver =  domain[15] == null? 0d : (Double) domain[15];
		dto.timeOver = domain[16] == null? 0: (Integer) domain[16];;
		return dto;
	}

}
