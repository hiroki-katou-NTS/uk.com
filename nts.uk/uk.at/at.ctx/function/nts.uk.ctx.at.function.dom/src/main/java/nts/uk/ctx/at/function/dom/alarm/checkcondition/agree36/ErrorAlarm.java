package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import lombok.AllArgsConstructor;

/**
 * 36協定エラーアラーム
 * @author yennth
 *
 */
@AllArgsConstructor
public enum ErrorAlarm {
	/** エラー */
	Error(0),
	/** アラーム */
	Alarm(1),
	/**上限規制 */
	Upper(2);
	public final int value;
}
