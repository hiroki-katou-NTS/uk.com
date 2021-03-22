package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.otheritem;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         その他項目の反映
 */
public class ReflectOtherItems {

	public static void process(ApplicationTimeShare overTimeApp, DailyRecordOfApplication dailyApp,
			OthersReflect othersReflect) {

		// [乖離理由を反映する]をチェック
		if (othersReflect.getReflectDivergentReasonAtr() == NotUseAtr.USE) {
			// 乖離理由の反映
			ReflectReasonDissociation.process(dailyApp, overTimeApp.getReasonDissociation());
		}

		// [加給時間を反映する]をチェック
		if (othersReflect.getReflectPaytimeAtr() == NotUseAtr.USE) {
			// 加給時間の反映
			ReflectApplicationTime.process(overTimeApp.getApplicationTime(), dailyApp,
					Optional.empty());
		}

	}

}
