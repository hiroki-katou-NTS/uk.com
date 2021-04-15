package nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author thanhpv
 * @name 作業変更可能期間設定
 * @path UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.作業変更可能期間設定.作業変更可能期間設定
 */
@Getter
@AllArgsConstructor
public class WorkChangeablePeriodSetting extends AggregateRoot {
	
	/** 経過月数 */
	private ElapsedMonths elapsedMonths;

	public static interface Require {
		//[R-1] 作業変更可能期間設定を取得する
		//作業変更可能期間設定Repository.get(会社ID)	
		WorkChangeablePeriodSetting get(String companyId);
		
		//[R-2] 締め期間を取得する
		//アルゴリズム.社員に対応する締め期間を取得する(社員ID,基準日)
		DatePeriod getPeriod(String employeeId, GeneralDate date);
	}
}
