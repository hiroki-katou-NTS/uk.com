package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 60超過時間表示情報パラメータ - 残数詳細
 */
@Data
public class RemainNumberDetailDto {

	/** 発生月 */
	private Integer occurrenceMonth;

	/** 使用日 */
	private GeneralDate usageDate;

	/** 発生時間 */
	private Integer occurrenceTime;

	/** 使用時間 */
	private Integer usageTime;

	/** 期限日 */
	private GeneralDate deadline;

	/** 作成区分 */
	private Integer creationCategory;
	
	private List<RemainNumberDetailDto> childRemainNumberDetailDtos;

}
