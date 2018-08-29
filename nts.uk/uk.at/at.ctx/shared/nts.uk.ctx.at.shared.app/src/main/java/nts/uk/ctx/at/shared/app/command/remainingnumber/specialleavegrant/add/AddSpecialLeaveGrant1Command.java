package nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
public class AddSpecialLeaveGrant1Command extends PeregDomainDto {

	private String specialid;
	private String cid;
	private int specialLeaCode;
	
	@PeregEmployeeId
	private String sid;

	// 付与日
	@PeregItem("IS00409")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	// 期限日
	@PeregItem("IS00410")
	private GeneralDate deadlineDate;

	// 期限切れ状態
	@PeregItem("IS00411")
	private BigDecimal expStatus;

	// 使用状況
	@PeregItem("IS00412")
	private BigDecimal registerType;

	// 付与日数
	@PeregItem("IS00414")
	private BigDecimal numberDayGrant;

	// 付与時間
	@PeregItem("IS00415")
	private BigDecimal timeGrant;

	// 使用日数
	@PeregItem("IS00417")
	private BigDecimal numberDayUse;

	// 使用時間
	@PeregItem("IS00418")
	private BigDecimal timeUse;

	//
	private int useSavingDays;

	// 上限超過消滅日数
	@PeregItem("IS00419")
	private BigDecimal numberDaysOver;

	// 上限超過消滅時間
	@PeregItem("IS00420")
	private BigDecimal timeOver;

	// 残日数
	@PeregItem("IS00422")
	private BigDecimal numberDayRemain;

	// 残時間
	@PeregItem("IS00423")
	private BigDecimal timeRemain;


}
