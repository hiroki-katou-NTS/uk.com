package nts.uk.ctx.at.record.ws.dialog.sixtyhour.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class UsageDateDto {

	/** 使用時間 */
	private Integer usageTime;

	/** 使用日 */
	private GeneralDate usageDate;

	/** 作成区分 */
	private Integer creationCategory;
}
