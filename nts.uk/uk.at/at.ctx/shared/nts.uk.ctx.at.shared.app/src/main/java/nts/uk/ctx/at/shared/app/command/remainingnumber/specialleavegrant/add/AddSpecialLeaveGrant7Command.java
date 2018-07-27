package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialLeaveGrant7Command {

	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00499")
	private GeneralDate grantDate;

	// 期限日
	@PeregItem("IS00500")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00501")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00502")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00504")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00505")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00507")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00508")
	private BigDecimal timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00509")
	private BigDecimal numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00510")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00512")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00513")
	private BigDecimal timeRemain;
}
