package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveDestination;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         時間休暇申請の反映
 */
public class SCRCReflectTimeLeaveApp {

	public static List<Integer> reflect(TimeLeaveApplicationShare appTimeLeav,
			DailyRecordOfApplication dailyApp, TimeLeaveApplicationReflect reflectTimeLeav) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [input. 時間休暇申請. 詳細(List)]から、反映するものだけを抽出
		List<TimeLeaveApplicationDetailShare> leaveApplicationDetails = appTimeLeav.getLeaveApplicationDetails()
				.stream().filter(x -> {
					return extractOnlyReflected(x, reflectTimeLeav.getDestination());
				}).collect(Collectors.toList());

		// 控除時間と相殺する時間休暇を反映
		lstItemId.addAll(ReflectTimeVacationOffsetDeductTime.process(leaveApplicationDetails, dailyApp,
				reflectTimeLeav.getCondition(), reflectTimeLeav.getReflectActualTimeZone()));

		return lstItemId;
	}

	// [input. 時間休暇申請. 詳細(List)]から、反映するものだけを抽出
	private static boolean extractOnlyReflected(TimeLeaveApplicationDetailShare timeLeavAppDetail,
			TimeLeaveDestination destination) {
		switch (timeLeavAppDetail.getAppTimeType()) {
		case ATWORK:
			return destination.getFirstBeforeWork() == NotUseAtr.USE;
		case OFFWORK:
			return destination.getFirstAfterWork() == NotUseAtr.USE;
		case ATWORK2:
			return destination.getSecondBeforeWork() == NotUseAtr.USE;
		case OFFWORK2:
			return destination.getSecondAfterWork() == NotUseAtr.USE;
		case PRIVATE:
			return destination.getPrivateGoingOut() == NotUseAtr.USE;
		case UNION:
			return destination.getUnionGoingOut() == NotUseAtr.USE;
		default:
			return false;
		}
	}
}
