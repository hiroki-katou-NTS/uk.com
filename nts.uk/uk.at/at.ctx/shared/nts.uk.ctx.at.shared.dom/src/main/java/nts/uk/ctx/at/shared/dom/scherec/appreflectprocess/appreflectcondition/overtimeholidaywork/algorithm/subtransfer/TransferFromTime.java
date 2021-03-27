package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import java.util.List;

import lombok.val;

/**
 * @author thanh_nx
 *
 *         時間から振り替える
 */
public class TransferFromTime {

	public static List<MaximumTime> process(int tranferableTime, List<MaximumTime> timeAfterReflectApp) {

		// input.振替をした後の時間（List）でループ
		for (MaximumTime detail : timeAfterReflectApp) {
			// 振替可能時間をチェック
			if (tranferableTime <= 0) {
				return timeAfterReflectApp;
			}

			// 時間から振替時間を割り振る
			val result = TimeToTimeTransfer.process(tranferableTime, detail);
			tranferableTime = result.getLeft();
		}

		return timeAfterReflectApp;
	}

}
