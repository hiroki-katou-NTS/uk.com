package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import java.util.List;

import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL017_60H超休ダイアログ.A：60H超休参照.アルゴリズム.60超過時間表示情報パラメータ.60超過時間表示情報詳細
 * 
 * @author LienPTK
 */
@Data
public class OverTimeIndicationInformationDetails {

	/** 60H超休管理区分 */
	private boolean departmentOvertime60H;

	/** 残数情報 */
	private List<RemainNumberDetailDto> remainNumberDetailDtos;

	/** 繰越数 */
	private Integer carryoverNumber;

	/** 使用数 */
	private Integer usageNumber;

	/** 締め期間 */
	private DatePeriod deadline;

	/** 残数 */
	private Integer residual;

	/** 紐付け管理 */
	private List<PegManagementDto> pegManagementDtos;

}
