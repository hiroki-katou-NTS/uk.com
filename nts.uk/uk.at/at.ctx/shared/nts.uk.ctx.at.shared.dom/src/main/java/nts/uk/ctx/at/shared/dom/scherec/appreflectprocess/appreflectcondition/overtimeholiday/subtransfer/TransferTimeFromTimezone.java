package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.subtransfer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;

/**
 * @author thanh_nx
 *
 *         時間帯から振替時間を振り替える
 */
public class TransferTimeFromTimezone {

	public static Pair<Integer, List<MaximumTime>> process(int tranferableTime, MaximumTimeZone maxTimeZone,
			List<MaximumTime> maxTime, List<MaximumTime> timeAfterReflectApp) {

		// input. 最大時間帯（List）でループ
		for (Map.Entry<Integer, TimeSpanForDailyCalc> maxTimeZoneItem : maxTimeZone.getTimeSpan().entrySet()) {

			// 振替可能時間をチェック
			if (tranferableTime <= 0)

				return Pair.of(tranferableTime, timeAfterReflectApp);
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
			val tranferDetail = TransferTimeFromTimezoneDetail.process(tranferableTime,
					maxTimeItem.getTransferTime().v(), timeAfterReflectAppItem);
			tranferableTime = tranferDetail.getLeft();
		}

		return Pair.of(tranferableTime, timeAfterReflectApp);
	}
}
