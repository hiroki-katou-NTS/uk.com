package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildCareNurseStartdateInfoDto {
	/** 起算日からの子の看護介護休暇使用数 */
	private ChildCareNurseUsedNumberDto usedDays;
	/** 子の看護介護休暇残数 */
	private ChildCareNurseRemainingNumberDto remainingNumber ;
	/** 子の看護介護休暇上限日数 */
	private Double limitDays;

}
