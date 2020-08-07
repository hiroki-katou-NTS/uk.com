package nts.uk.ctx.at.request.app.find.dialog.superholiday;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * 60超過時間表示情報パラメータ - 残数詳細
 */
@Data
public class RemainNumberDetailDto {

	/** 発生月 */
	private YearMonth occurrenceMonth;

	/** 使用日 */
	private GeneralDate usageDate;

	/** 発生時間 */
	private int occurrenceTime;

	/** 使用時間 */
	private int usageTime;

	/** 期限日 */
	private GeneralDate deadline;

	/** 作成区分 */
	private int creationCategory;

}
