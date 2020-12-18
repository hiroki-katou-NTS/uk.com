package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildCareNurseUsedNumberDto {
	/** 子の看護介護休暇（使用日数） */
	private Double usedDay;
	/** 子の看護介護休暇（使用時間） */
	private Integer usedTimes;
}
