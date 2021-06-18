package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class OneTimeImport.
 *
 * @author LienPTK
 */
@Data
@AllArgsConstructor
public class OneTimeImport {
	/** エラーアラーム時間 */
	private int errorTime;

	/** エラーアラーム時間 */
	private int alarmTime;

	/** 上限時間 */
	private int upperLimit;
}
