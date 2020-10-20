package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;

/**
 * @author thanh_nx
 *
 *         反映した外出枠NOを取得する
 */
public class GetReflectOutGoFrameNumber {

	public static ReflectOutGoFrameNumberResult process(List<OutingTimeSheet> oTAfter, List<OutingTimeSheet> oTBefore) {

		// input.反映後の外出時間帯（List）でループ

		for (OutingTimeSheet after : oTAfter) {
			// 反映前の外出時間帯から同じ外出枠NOの時間帯を取得
			OutingTimeSheet before = oTBefore.stream()
					.filter(x -> x.getOutingFrameNo().v() == after.getOutingFrameNo().v()).findFirst().orElse(null);
			if (before == null) {
				return new ReflectOutGoFrameNumberResult(true, after.getOutingFrameNo().v());
			}

			// 外出と戻りの時刻を比較する
			int goOutBefore = Integer.MIN_VALUE, goOutAfter =  Integer.MIN_VALUE;
			int attBefore =  Integer.MIN_VALUE, attAfter =  Integer.MIN_VALUE;

			if (after.getGoOut().isPresent() && after.getGoOut().get().getStamp().isPresent()) {
				goOutAfter = after.getGoOut().get().getStamp().get().getAfterRoundingTime().v();
			}

			if (before.getGoOut().isPresent() && before.getGoOut().get().getStamp().isPresent()) {
				goOutBefore = before.getGoOut().get().getStamp().get().getAfterRoundingTime().v();
			}

			if (after.getComeBack().isPresent() && after.getComeBack().get().getStamp().isPresent()) {
				attAfter = after.getComeBack().get().getStamp().get().getAfterRoundingTime().v();
			}

			if (before.getComeBack().isPresent() && before.getComeBack().get().getStamp().isPresent()) {
				attBefore = before.getComeBack().get().getStamp().get().getAfterRoundingTime().v();
			}

			if (goOutBefore != goOutAfter || attBefore != attAfter) {
				return new ReflectOutGoFrameNumberResult(true, after.getOutingFrameNo().v());
			}
		}

		return new ReflectOutGoFrameNumberResult();
	}

	@AllArgsConstructor
	@Getter
	public static class ReflectOutGoFrameNumberResult {

		// 反映フラグ
		private boolean reflect;

		// 外出枠NO
		private Integer no;

		public ReflectOutGoFrameNumberResult() {
			this.reflect = false;
		}
	}
}
