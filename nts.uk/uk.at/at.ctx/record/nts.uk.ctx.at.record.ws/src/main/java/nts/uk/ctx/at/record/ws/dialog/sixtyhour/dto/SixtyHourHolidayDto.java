package nts.uk.ctx.at.record.ws.dialog.sixtyhour.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class SixtyHourHolidayDto {

	/** 60H超休管理区分 */
	private boolean departmentOvertime60H;

	/** 残数情報 */
	private List<RemainHourDetailDto> remainHourDetailDtos;

	/** 繰越数 */
	private Integer carryoverNumber;

	/** 使用数 */
	private Integer usageNumber;

	/** 締め期間 */
	private GeneralDate startDate;
	private GeneralDate endDate;

	/** 残数 */
	private Integer residual;

}
