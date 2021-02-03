package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

import java.util.List;

/**
 * 子の看護介護休暇集計結果
 * @author yuri_tamakoshi
 */

@Getter
@AllArgsConstructor
public class ChildCareNursePeriodImport {

	/** エラー情報 */
	private List<ChildCareNurseErrorsImport> childCareNurseErrors;
	/** 期間終了日の翌日時点での使用数 */
	private ChildCareNurseUsedNumber asOfPeriodEnd;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateDaysInfoImport startdateDays;
	/** 起算日を含む期間フラグ */
	private boolean startDateAtr;
	/** 集計期間の休暇情報*/
	private ChildCareNurseAggrPeriodDaysInfoImport aggrperiodinfo;

}