package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave;

import lombok.AllArgsConstructor;

/**
 * 指定日区分
 * @author shuichi_ishida
 */
@AllArgsConstructor
public enum SpecDateAtr {
	/**
	 * 基準日時点	
	 */
	CRITERIA(0),
	/**
	 * 当月の締め終了日時点
	 */
	CURRENT_CLOSURE_END(1);
	public final int value;
}
