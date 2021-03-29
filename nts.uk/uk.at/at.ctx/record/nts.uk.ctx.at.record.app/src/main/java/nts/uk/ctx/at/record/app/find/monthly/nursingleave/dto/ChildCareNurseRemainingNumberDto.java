package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildCareNurseRemainingNumberDto {
	/** 子の看護休暇使用日数 */
	private  Double usedDays;
	/** 子の看護休暇使用時間 */
	private Integer usedTime;
}
