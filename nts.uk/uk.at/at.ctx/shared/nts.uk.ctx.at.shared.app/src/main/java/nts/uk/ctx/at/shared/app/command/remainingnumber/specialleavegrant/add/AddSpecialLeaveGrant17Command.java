package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialLeaveGrant17Command {
	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00719")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	// 期限日
	@PeregItem("IS00720")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00721")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00722")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00724")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00725")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00727")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00728")
	private BigDecimal timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00729")
	private BigDecimal numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00730")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00732")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00733")
	private BigDecimal timeRemain;
}
