package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.otheritem.ReflectReasonDissociation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.reflectbreak.ReflectApplicationTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor4 refactor 4 その他項目の反映
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OthersReflect extends DomainObject {
	/**
	 * 乖離理由を反映する
	 */
	private NotUseAtr reflectDivergentReasonAtr;

//    private NotUseAtr reflectOptionalItemsAtr;

	/**
	 * 加給時間を反映する
	 */
	private NotUseAtr reflectPaytimeAtr;

	/**
	 * @author thanh_nx
	 *
	 *         その他項目の反映
	 */

	public void process(ApplicationTimeShare overTimeApp, DailyRecordOfApplication dailyApp) {

		// [乖離理由を反映する]をチェック
		if (this.getReflectDivergentReasonAtr() == NotUseAtr.USE) {
			// 乖離理由の反映
			ReflectReasonDissociation.process(dailyApp, overTimeApp.getReasonDissociation());
		}

		// [加給時間を反映する]をチェック
		if (this.getReflectPaytimeAtr() == NotUseAtr.USE) {
			// 加給時間の反映
			ReflectApplicationTime.process(overTimeApp.getApplicationTime(), dailyApp, Optional.empty());
		}

	}
}
