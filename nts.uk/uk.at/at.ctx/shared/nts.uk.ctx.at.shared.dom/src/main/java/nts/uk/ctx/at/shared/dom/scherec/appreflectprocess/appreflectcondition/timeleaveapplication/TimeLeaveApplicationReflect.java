package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4 refactor4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.時間休暇申請
 * 時間休暇申請の反映
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveApplicationReflect extends AggregateRoot {

	private String companyId;

	/**
	 * 実績の時間帯へ反映する
	 */
	private NotUseAtr reflectActualTimeZone;

	/**
	 * 時間休暇の反映先
	 */
	private TimeLeaveDestination destination;

	/**
	 * 時間休暇を反映する
	 */
	private TimeLeaveAppReflectCondition condition;

	/**
	 * @author thanh_nx
	 *
	 *         時間休暇申請の反映
	 */

	public DailyAfterAppReflectResult reflect(TimeLeaveApplicationShare appTimeLeav, DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// [input. 時間休暇申請. 詳細(List)]から、反映するものだけを抽出
		List<TimeLeaveApplicationDetailShare> leaveApplicationDetails = appTimeLeav.getLeaveApplicationDetails()
				.stream().filter(x -> {
					return extractOnlyReflected(x, this.getDestination());
				}).collect(Collectors.toList());

		// 控除時間と相殺する時間休暇を反映
		lstItemId.addAll(this.getCondition().process(leaveApplicationDetails, dailyApp, this.getReflectActualTimeZone()).getLstItemId());

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
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
