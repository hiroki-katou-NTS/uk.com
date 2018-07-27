package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialLeaveGrant2Command {
	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00424")
	private GeneralDate grantDate;

	// 期限日
	@PeregItem("IS00425")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00426")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00427")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00429")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00430")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00432")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00433")
	private BigDecimal timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00434")
	private BigDecimal numberDaysOver;

	//  上限超過消滅時間
	@PeregItem("IS00435")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00437")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00438")
	private BigDecimal timeRemain;

}
