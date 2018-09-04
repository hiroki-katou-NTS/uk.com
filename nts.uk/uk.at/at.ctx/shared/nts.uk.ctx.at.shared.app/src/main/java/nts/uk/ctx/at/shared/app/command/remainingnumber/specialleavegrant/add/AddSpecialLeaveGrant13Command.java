package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialLeaveGrant13Command {
 
	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00659")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	// 期限日
	@PeregItem("IS00660")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00661")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00662")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00664")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00665")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00667")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00668")
	private BigDecimal timeUse;

	//
	private double useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00669")
	private BigDecimal numberDaysOver;

	//  上限超過消滅時間
	@PeregItem("IS00670")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00672")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00673")
	private BigDecimal timeRemain;
}
