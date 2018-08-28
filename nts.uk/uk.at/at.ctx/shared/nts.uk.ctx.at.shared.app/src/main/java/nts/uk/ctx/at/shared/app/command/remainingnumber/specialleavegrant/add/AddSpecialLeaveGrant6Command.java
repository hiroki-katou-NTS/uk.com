package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialLeaveGrant6Command {

	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00484")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	// 期限日
	@PeregItem("IS00485")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00486")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00487")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00489")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00490")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00492")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00493")
	private BigDecimal timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00494")
	private BigDecimal numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00495")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00497")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00498")
	private BigDecimal timeRemain;
}
