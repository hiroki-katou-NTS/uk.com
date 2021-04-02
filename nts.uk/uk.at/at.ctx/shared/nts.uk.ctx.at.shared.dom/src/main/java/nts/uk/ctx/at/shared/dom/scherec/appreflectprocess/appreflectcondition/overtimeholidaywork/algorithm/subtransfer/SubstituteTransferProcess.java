package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;

/**
 * @author thanh_nx
 *
 *         代休振替処理
 */
public class SubstituteTransferProcess {
	public static List<MaximumTime> process(MaximumTimeZone maxTimeZone, List<MaximumTime> maxTime,
			List<MaximumTime> timeAfterReflectApp) {

		// 振替可能時間を算出する
		int tranferableTime = maxTime.stream().collect(Collectors.summingInt(x -> x.getTransferTime().v()));

		// 時間帯から振替時間を振り替える
		TransferResultAllFrame transferTimeResult = processTransferFromTransTimeZone(new AttendanceTime(tranferableTime), maxTimeZone,
				maxTime, timeAfterReflectApp);

		// 時間帯から時間を振り替える
		TransferResultAllFrame transferTimeAfterResult = processTransferFromTimeZone(new AttendanceTime(tranferableTime),
				maxTimeZone, maxTime, transferTimeResult.getMaximumTime());

		// 未割当の時間を算出
		int unallocatedTime = transferTimeAfterResult.getMaximumTime().stream()
				.collect(Collectors.summingInt(x -> x.getTime().v()));

		if (unallocatedTime <= 0) {
			return transferTimeAfterResult.getMaximumTime();
		}

		// 時間から振り替える
		return processTimeToTimeTransferAll(tranferableTime, timeAfterReflectApp);
	}

	// 時間帯から振替時間を振り替える
	private static TransferResultAllFrame processTransferFromTransTimeZone(AttendanceTime tranferableTime, MaximumTimeZone maxTimeZone,
			List<MaximumTime> maxTime, List<MaximumTime> timeAfterReflectApp) {

		// input. 最大時間帯（List）でループ
		for (Map.Entry<Integer, TimeSpanForDailyCalc> maxTimeZoneItem : maxTimeZone.getTimeSpan().entrySet()) {

			// 振替可能時間をチェック
			if (tranferableTime.v() <= 0)

				return new TransferResultAllFrame(tranferableTime, timeAfterReflectApp);
			// 処理中の最大時間帯に該当する枠NOの振替時間を取得
			MaximumTime maxTimeItem = maxTime.stream().filter(x -> x.getNo() == maxTimeZoneItem.getKey()).findFirst()
					.orElse(null);
			if (maxTimeItem == null || maxTimeItem.getTime().v() <= 0)
				continue;

			// [input.振替をした後の時間（List）]をチェック
			MaximumTime timeAfterReflectAppItem = timeAfterReflectApp.stream()
					.filter(x -> x.getNo() == maxTimeZoneItem.getKey()).findFirst().orElse(null);
			if (timeAfterReflectAppItem == null || timeAfterReflectAppItem.getTime().v() <= 0)
				continue;

			// 時間の振替
			TransferResultAllFrame tranferDetail = processTimeZoneDetail(tranferableTime.v(),
					maxTimeItem.getTransferTime().v(), timeAfterReflectAppItem);
			tranferableTime = tranferDetail.getTime();
		}

		return new TransferResultAllFrame(tranferableTime, timeAfterReflectApp);
	}

	// 時間帯から時間を振り替える
	private static TransferResultAllFrame  processTransferFromTimeZone(AttendanceTime tranferableTime, MaximumTimeZone maxTimeZone,
			List<MaximumTime> maxTime, List<MaximumTime> timeAfterReflectApp) {

		// input. 最大時間帯（List）でループ
		for (Map.Entry<Integer, TimeSpanForDailyCalc> maxTimeZoneItem : maxTimeZone.getTimeSpan().entrySet()) {

			// 振替可能時間をチェック
			if (tranferableTime.v() <= 0)

				return new TransferResultAllFrame(tranferableTime, timeAfterReflectApp);
			// 処理中の最大時間帯に該当する枠NOの振替時間を取得
			MaximumTime maxTimeItem = maxTime.stream().filter(x -> x.getNo() == maxTimeZoneItem.getKey()).findFirst()
					.orElse(null);
			if (maxTimeItem == null || maxTimeItem.getTime().v() <= 0)
				continue;

			// [input.振替をした後の時間（List）]をチェック
			MaximumTime timeAfterReflectAppItem = timeAfterReflectApp.stream()
					.filter(x -> x.getNo() == maxTimeZoneItem.getKey()).findFirst().orElse(null);
			if (timeAfterReflectAppItem == null || timeAfterReflectAppItem.getTime().v() <= 0)
				continue;

			// 時間の振替
			TransferResultAllFrame tranferDetail = processTimeZoneDetail(tranferableTime.v(),
					maxTimeItem.getTransferTime().v(), timeAfterReflectAppItem);
			tranferableTime = tranferDetail.getTime();
		}

		return new TransferResultAllFrame(tranferableTime, timeAfterReflectApp);
	}

	// 時間帯から時間の振替
	private static TransferResultAllFrame processTimeZoneDetail(int tranferableTime, int maxTime,
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

		return new TransferResultAllFrame(new AttendanceTime(remainTransfer), Arrays.asList(timeAfterReflectApp));
	}

	/**
	 *
	 * 時間から振り替える
	 */
	private static List<MaximumTime> processTimeToTimeTransferAll(int tranferableTime,
			List<MaximumTime> timeAfterReflectApp) {

		// input.振替をした後の時間（List）でループ
		for (MaximumTime detail : timeAfterReflectApp) {
			// 振替可能時間をチェック
			if (tranferableTime <= 0) {
				return timeAfterReflectApp;
			}

			// 時間から振替時間を割り振る
			val result = processTimeToTimeTransfer(tranferableTime, detail);
			tranferableTime = result.getTime().v();
		}

		return timeAfterReflectApp;
	}

	/**
	 *
	 * 時間から時間の振替
	 */
	private static TransferResultAllFrame processTimeToTimeTransfer(int tranferableTime,
			MaximumTime timeReflectApp) {

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

		return new TransferResultAllFrame(new AttendanceTime(tranferableTime), Arrays.asList(timeReflectApp));
	}
}
