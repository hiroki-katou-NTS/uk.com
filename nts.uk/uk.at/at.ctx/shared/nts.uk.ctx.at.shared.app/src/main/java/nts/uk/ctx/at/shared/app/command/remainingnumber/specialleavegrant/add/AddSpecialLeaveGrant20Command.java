package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialLeaveGrant20Command {

	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00764")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	// 期限日
	@PeregItem("IS00765")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00766")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00767")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00769")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00770")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00772")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00773")
	private BigDecimal timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00774")
	private BigDecimal numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00775")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00777")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00778")
	private BigDecimal timeRemain;
}
