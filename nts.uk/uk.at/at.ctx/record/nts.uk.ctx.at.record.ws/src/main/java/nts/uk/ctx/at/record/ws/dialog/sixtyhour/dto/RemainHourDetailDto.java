package nts.uk.ctx.at.record.ws.dialog.sixtyhour.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class RemainHourDetailDto {
	/** 発生月 */
	private Integer occurrenceMonth;

	/** 発生時間 */
	private Integer occurrenceTime;

	/** 期限日 */
	private GeneralDate deadline;
	
	private List<UsageDateDto> usageDateDtos;
}
