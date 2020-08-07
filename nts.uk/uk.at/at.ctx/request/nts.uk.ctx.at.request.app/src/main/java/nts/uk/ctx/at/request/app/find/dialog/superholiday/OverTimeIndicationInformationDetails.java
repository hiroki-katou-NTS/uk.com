package nts.uk.ctx.at.request.app.find.dialog.superholiday;

import java.util.List;

import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 60超過時間表示情報パラメータ - 60超過時間表示情報詳細
 */
@Data
public class OverTimeIndicationInformationDetails {

	/** 60H超休管理区分 */
	private boolean departmentOvertime60H;

	/** 残数情報 */
	private List<RemainNumberDetailDto> remainNumberDetailDtos;

	/** 繰越数 */
	private int carryoverNumber;

	/** 使用数 */
	private int usageNumber;

	/** 締め期間 */
	private DatePeriod deadline;

	/** 残数 */
	private int residual;

	/** 紐付け管理 */
	private List<PegManagementDto> pegManagementDtos;

}
