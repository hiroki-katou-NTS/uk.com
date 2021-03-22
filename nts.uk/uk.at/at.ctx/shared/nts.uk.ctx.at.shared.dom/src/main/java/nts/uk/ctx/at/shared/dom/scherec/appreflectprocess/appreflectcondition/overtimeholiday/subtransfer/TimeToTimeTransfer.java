package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author thanh_nx
 *
 *         時間から時間の振替
 */
public class TimeToTimeTransfer {

	public static Pair<Integer, MaximumTime> process(int tranferableTime, MaximumTime timeReflectApp) {

		// [時間外労働時間.時間]と[振替可能時間]を比較
		if (timeReflectApp.getTime().v() <= tranferableTime) {
			// [時間外労働時間.時間]を振替時間に加算
			timeReflectApp.setTransferTime(
					new AttendanceTime(timeReflectApp.getTransferTime().v() + timeReflectApp.getTime().v()));
		} else {
			// [振替可能時間]を振替時間に加算
			timeReflectApp.setTransferTime(new AttendanceTime(timeReflectApp.getTransferTime().v() + tranferableTime));
		}

		// 振り替えた時間を[時間外労働時間.時間]から減算する
		int time = timeReflectApp.getTime().v() - timeReflectApp.getTransferTime().v();
		timeReflectApp.setTime(new AttendanceTime(time));

		tranferableTime -= timeReflectApp.getTransferTime().v();
		// 振り替えた時間を振替可能時間から減算する

		return Pair.of(tranferableTime, timeReflectApp);
	}
}
