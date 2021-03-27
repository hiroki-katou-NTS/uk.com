package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author thanh_nx
 *
 *         時間帯から時間の振替
 */
public class TransferTimeFromTimezoneDetail {

	public static Pair<Integer, MaximumTime> process(int tranferableTime, int maxTime,
			MaximumTime timeAfterReflectApp) {

		// [input.振替可能時間] と [input.最大時間]を比較

		if (tranferableTime <= maxTime) {
			// [時間外労働時間.時間]と[振替可能時間]を比較
			if (timeAfterReflectApp.getTime().v() <= tranferableTime) {

				/// [時間外労働時間.時間]を振替時間に加算
				timeAfterReflectApp.setTransferTime(new AttendanceTime(
						timeAfterReflectApp.getTransferTime().v() + timeAfterReflectApp.getTime().valueAsMinutes()));
			} else {

				/// [振替可能時間]を振替時間に加算
				timeAfterReflectApp.setTransferTime(
						new AttendanceTime(timeAfterReflectApp.getTransferTime().v() + tranferableTime));
			}
		} else {
			// [時間外労働時間.時間]と[最大時間]を比較
			if (timeAfterReflectApp.getTime().v() <= maxTime) {

				/// [時間外労働時間.時間]を振替時間に加算
				timeAfterReflectApp.setTransferTime(new AttendanceTime(
						timeAfterReflectApp.getTransferTime().v() + timeAfterReflectApp.getTime().valueAsMinutes()));
			} else {

				/// [最大時間]を振替時間に加算
				timeAfterReflectApp
						.setTransferTime(new AttendanceTime(timeAfterReflectApp.getTransferTime().v() + maxTime));
			}

		}

		// 振り替えた時間を[時間外労働時間.時間]から減算する
		Integer remainTransferApp = timeAfterReflectApp.getTime().v() - timeAfterReflectApp.getTransferTime().v();
		timeAfterReflectApp.setTime(new AttendanceTime(remainTransferApp));

		// 振り替えた時間を振替可能時間から減算する
		Integer remainTransfer = tranferableTime - timeAfterReflectApp.getTransferTime().v();

		return Pair.of(remainTransfer, timeAfterReflectApp);
	}

}
