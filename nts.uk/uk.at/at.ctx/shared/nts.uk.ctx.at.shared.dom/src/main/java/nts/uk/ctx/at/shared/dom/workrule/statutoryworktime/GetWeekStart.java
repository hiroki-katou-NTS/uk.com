package nts.uk.ctx.at.shared.dom.workrule.statutoryworktime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 週開始を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWeekStart {

	/** 通常・変形勤務の法定労働時間 */
	//@Inject
	//*****（未）　法定労働時間ドメインから取得するのではなく、週開始用ドメインを作成する予定。2018.4.19 shuichi_ishida
	
	/**
	 * 週開始を取得する
	 * @param workingSystem 労働制
	 * @return 週開始
	 */
	public Optional<WeekStart> algorithm(WorkingSystem workingSystem){
		//*****（未）　パラメータは、法定労働時間の取得メソッドに揃える
		
		Optional<WeekStart> returnValue = Optional.empty();

		// 労働制を確認
		if (workingSystem == WorkingSystem.FLEX_TIME_WORK) return returnValue;
		if (workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE) return returnValue;

		// 時間設定を取得
		//*****（未）　リポジトリを使って、設定を取得してくる。StatutoryWorkTimeSetを取得予定。
		
		// 週開始を取得
		//*****（未）　仮に、締め開始日を返却。
		returnValue = Optional.of(WeekStart.TighteningStartDate);
		
		return returnValue;
	}
}
