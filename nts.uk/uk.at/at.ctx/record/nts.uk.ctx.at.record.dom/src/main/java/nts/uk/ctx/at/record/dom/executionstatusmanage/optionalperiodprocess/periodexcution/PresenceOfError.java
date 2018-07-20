package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution;

import lombok.AllArgsConstructor;
/**
 * エラーの有無
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum PresenceOfError {

		// 0:エラーあり
		ERROR(0, "エラーあり"),
		// 1:エラーなし
		NO_ERROR(1, "エラーなし");

		public final int value;
		public final String name;
}
