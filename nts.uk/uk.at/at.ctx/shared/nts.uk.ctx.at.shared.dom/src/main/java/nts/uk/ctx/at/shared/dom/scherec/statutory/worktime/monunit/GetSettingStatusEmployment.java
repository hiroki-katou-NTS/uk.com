package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.util.List;

/**
 * DS: 雇用別の設定状態を取得
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.月単位の法定労働時間.雇用別の設定状態を取得
 * @author chungnt
 *
 */

public class GetSettingStatusEmployment {


	public static interface Require {
		
		//	[R-1] 雇用別月単位労働時間を取得する(会社ID)
		List<MonthlyWorkTimeSetEmp> findEmploymentbyCid(String cid);
		
	}
}
