package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子の看護介護休暇集計結果
 * @author yuri_tamakoshi
 */

@Getter
@AllArgsConstructor
public class ChildCareNursePeriodExport {

	/** エラー情報 */
	private List<ChildCareNurseErrorsExport> childCareNurseErrors;
	/** 期間終了日の翌日時点での使用数 */
	private ChildCareNurseUsedNumberExport asOfPeriodEnd;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateDaysInfoExport  startdateDays;
	/** 起算日を含む期間フラグ */
	private boolean startDateAtr;
	/** 集計期間の休暇情報*/
	private ChildCareNurseAggrPeriodDaysInfoExport aggrperiodinfo;

}