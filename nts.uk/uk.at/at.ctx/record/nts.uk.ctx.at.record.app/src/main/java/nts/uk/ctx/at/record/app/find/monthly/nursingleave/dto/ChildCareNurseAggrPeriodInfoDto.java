package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildCareNurseAggrPeriodInfoDto {
	/** 子の看護介護休暇の時間休暇使用回数 */
	private Integer usedCount;
	/** 子の看護介護休暇の時間休暇使用日数 */
	private Integer usedDays;
	/** 集計期間の子の看護介護休暇使用数 */
	private  ChildCareNurseUsedNumberDto aggrPeriodUsedNumber;
}
