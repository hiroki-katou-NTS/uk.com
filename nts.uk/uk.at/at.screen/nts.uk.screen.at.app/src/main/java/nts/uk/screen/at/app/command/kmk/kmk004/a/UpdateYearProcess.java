package nts.uk.screen.at.app.command.kmk.kmk004.a;

import javax.ejb.Stateless;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class UpdateYearProcess {

	public static void updateProcess(RegisterYearCommand cmd) {
		// 親画面からのパラメータ「勤務の種類」をチェックする
		if (cmd.getWorkTypeMode().equals(WorkTypeMode.REGULAR_WORK)) {
			// 通常勤務の場合
			// サブメニュー「職場」から起動しているかチェック（パラメータ.職場IDを持っている場合）
			if (cmd.getScreenMode().equals(ScreenMode.WORK_PLACE)) {
				cmd.getItemId();
				// ドメインモデル「職場別通常勤務月間労働時間」を更新する
				//update domain 
			}
		}

		if (cmd.getWorkTypeMode().equals(WorkTypeMode.UNUSUAL_WORK)) {
//変形労働の場合
		}
	}

}
