package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;

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
		val transferTimeResult = TransferTimeFromTimezone.process(tranferableTime, maxTimeZone, maxTime,
				timeAfterReflectApp);

		// 時間帯から時間を振り替える
		val transferTimeAfterResult = TransferTimeFromTimezone.process(tranferableTime, maxTimeZone, maxTime,
				transferTimeResult.getRight());

		// 未割当の時間を算出
		int unallocatedTime = transferTimeAfterResult.getRight().stream()
				.collect(Collectors.summingInt(x -> x.getTime().v()));

		if (unallocatedTime <= 0) {
			return transferTimeAfterResult.getRight();
		}

		// 時間から振り替える
		return TransferFromTime.process(tranferableTime, timeAfterReflectApp);
	}

}
