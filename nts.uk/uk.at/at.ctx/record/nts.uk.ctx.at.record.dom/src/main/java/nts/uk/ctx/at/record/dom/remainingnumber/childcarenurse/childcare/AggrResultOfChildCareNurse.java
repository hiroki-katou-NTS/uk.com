package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

/**
 * 子の看護介護休暇集計結果
  * @author yuri_tamakoshi
 */

@Getter
@Setter
public class AggrResultOfChildCareNurse {

	/** エラー情報 */
	private List<ChildCareNurseErrors> childCareNurseErrors;
	/** 期間終了日の翌日時点での使用数 */
	private ChildCareNurseUsedNumber asOfPeriodEnd;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateDaysInfo  startdateDays;
	/** 起算日を含む期間フラグ */
	private boolean startDateAtr;
	/** 集計期間の休暇情報*/
	private ChildCareNurseAggrPeriodDaysInfo aggrperiodinfo;

	/**
	 * コンストラクタ
	 */
	public AggrResultOfChildCareNurse(){
		this.childCareNurseErrors = new ArrayList<>();
		this.asOfPeriodEnd = new ChildCareNurseUsedNumber();
		this.startdateDays = new ChildCareNurseStartdateDaysInfo();
		this.startDateAtr = false;
		this.aggrperiodinfo = new ChildCareNurseAggrPeriodDaysInfo();
	}

	/**
	 * ファクトリー
	 * @param childCareNurseErrors エラー情報
	 * @param asOfPeriodEnd 期間終了日の翌日時点での使用数
	 * @param startdateDays  起算日からの休暇情報
	 * @param startDateAtr 起算日を含む期間フラグ
	 * @param aggrperiodinfo  集計期間の休暇情報
	 * @return 子の看護介護休暇集計結果
	 */
	public static AggrResultOfChildCareNurse of (
			List<ChildCareNurseErrors> childCareNurseErrors,
			ChildCareNurseUsedNumber asOfPeriodEnd,
			ChildCareNurseStartdateDaysInfo startdateDays,
			boolean startDateAtr,
			ChildCareNurseAggrPeriodDaysInfo aggrperiodinfo){

		AggrResultOfChildCareNurse domain = new AggrResultOfChildCareNurse();
		domain.childCareNurseErrors = childCareNurseErrors;
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.startdateDays = startdateDays;
		domain.startDateAtr = startDateAtr;
		domain.aggrperiodinfo = aggrperiodinfo;
		return domain;
	}
}
