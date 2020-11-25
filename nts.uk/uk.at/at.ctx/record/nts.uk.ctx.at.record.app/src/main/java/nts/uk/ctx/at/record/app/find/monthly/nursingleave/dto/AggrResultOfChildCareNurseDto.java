package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AggrResultOfChildCareNurseDto {
	/** エラー情報 */
	private List<ChildCareNurseErrorsDto> childCareNurseErrors;
	/** 期間終了日の翌日時点での使用数 */
	private ChildCareNurseUsedNumberDto asOfPeriodEnd;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateDaysInfoDto  startdateDays;
	/** 起算日を含む期間フラグ */
	private boolean startDateAtr;
	/** 集計期間の休暇情報*/
	private ChildCareNurseAggrPeriodDaysInfoDto aggrperiodinfo;
}
