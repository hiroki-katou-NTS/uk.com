package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CorrectDeadline;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * スケジュール修正の修正可能開始日を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.権限制御.スケジュール修正の修正可能開始日を取得する
 * @author hiroko_miura
 *
 */
public class ScheModifyStartDateService {

	/**
	 * 取得する
	 * @param require
	 * @param roleID
	 * @return
	 */
	public static GeneralDate getModifyStartDate(Require require, String roleID) {
		Optional<ScheAuthModifyDeadline> modifyDeadline =require.getScheAuthModifyDeadline(roleID);
		
		if (!modifyDeadline.isPresent()) {
			modifyDeadline = Optional.of(new ScheAuthModifyDeadline(roleID, NotUseAtr.NOT_USE, new CorrectDeadline(0)));
		}
		
		return modifyDeadline.get().modifiableDate();
	}
	
	public static interface Require{
		/**
		 * スケジュール修正の修正期限を取得する
		 * @param roleID
		 * @return
		 */
		Optional<ScheAuthModifyDeadline> getScheAuthModifyDeadline(String roleID);
	}
}
