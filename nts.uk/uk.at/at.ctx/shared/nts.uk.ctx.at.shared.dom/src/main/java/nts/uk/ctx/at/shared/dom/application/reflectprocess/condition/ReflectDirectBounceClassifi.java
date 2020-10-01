package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         直行直帰区分の反映
 */
public class ReflectDirectBounceClassifi {

	public static List<Integer> reflect(DailyRecordOfApplication dailyApp, NotUseAtr returnHome, NotUseAtr goOut) {
		// [input. 直行区分]のチェック
		List<Integer> itemIds = new ArrayList<Integer>();
		if (goOut == NotUseAtr.USE) {
			dailyApp.getWorkInformation().setGoStraightAtr(EnumAdaptor.valueOf(goOut.value, NotUseAttribute.class));
			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(859));
			itemIds.add(859);
		}

		// [input. 直帰区分]のチェック
		if (returnHome == NotUseAtr.USE) {
			dailyApp.getWorkInformation()
					.setBackStraightAtr(EnumAdaptor.valueOf(returnHome.value, NotUseAttribute.class));
			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(860));
			itemIds.add(860);
		}
		return itemIds;
	}

}
