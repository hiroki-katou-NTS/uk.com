package nts.uk.ctx.at.shared.dom.workrule.statutoryworktime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

/**
 * 週開始を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWeekStart {

	/** 通常・変形勤務の法定労働時間 */
	//@Inject
	//*****（未）　取得リポジトリは、別担当で作成中。
	
	/**
	 * 取得
	 * @param workingSystem 労働制
	 * @return 週開始
	 */
	public Optional<WeekStart> get(WorkingSystem workingSystem){
		//*****（未）　パラメータは、法定労働時間の取得メソッドに揃える
		
		Optional<WeekStart> returnValue = Optional.empty();

		// 労働制を確認
		if (workingSystem.isFlexTimeWork()) return returnValue;
		if (workingSystem.isExcludedWorkingCalculate()) return returnValue;

		// 時間設定を取得
		//*****（未）　リポジトリを使って、設定を取得してくる。StatutoryWorkTimeSetを取得予定。
		
		// 週開始を取得
		//*****（未）　仮に、締め開始日を返却。
		returnValue = Optional.of(WeekStart.TighteningStartDate);
		
		return returnValue;
	}
}
