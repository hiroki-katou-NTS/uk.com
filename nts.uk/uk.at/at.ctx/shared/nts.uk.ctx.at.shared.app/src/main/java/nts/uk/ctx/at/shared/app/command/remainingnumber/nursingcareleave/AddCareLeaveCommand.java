package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddCareLeaveCommand {
	// 社員ID
	@PeregEmployeeId
	private String sId;

	// 子の看護休暇管理
	@PeregItem("IS00375")
	private BigDecimal childCareUseArt;

	@PeregItem("IS00376")
	// 子の看護上限設定
	private BigDecimal childCareUpLimSet;

	@PeregItem("IS00377")
	// 本年度の子の看護上限日数
	private BigDecimal childCareThisFiscal;

	@PeregItem("IS00378")
	// 次年度の子の看護上限日数
	private BigDecimal childCareNextFiscal;

	// 子の看護休暇管理
	@PeregItem("IS00379")
	private BigDecimal childCareUsedDays;

	// 介護休暇管理
	@PeregItem("IS00380")
	private BigDecimal careUseArt;

	@PeregItem("IS00381")
	// 介護上限設定
	private BigDecimal careUpLimSet;

	@PeregItem("IS00382")
	// 本年度の介護上限日数
	private BigDecimal careThisFiscal;

	@PeregItem("IS00383")
	// 次年度の介護上限日数
	private BigDecimal careNextFiscal;

	// 介護使用日数
	@PeregItem("IS00384")
	private BigDecimal careUsedDays;
}
