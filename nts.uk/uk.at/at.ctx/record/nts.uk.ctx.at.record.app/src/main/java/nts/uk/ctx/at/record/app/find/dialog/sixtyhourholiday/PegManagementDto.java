package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * 紐付け管理
 */
@Data
public class PegManagementDto {
	
	/** 発生月 */
	private YearMonth occurrenceMonth;
	
	/** 使用日 */
	private GeneralDate usageDate;
	
	/** 使用数 */
	private Integer usageNumber;

}
