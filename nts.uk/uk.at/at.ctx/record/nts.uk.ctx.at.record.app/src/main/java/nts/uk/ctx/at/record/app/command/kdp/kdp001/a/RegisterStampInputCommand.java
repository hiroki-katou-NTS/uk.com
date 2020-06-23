package nts.uk.ctx.at.record.app.command.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStampInputCommand {
	/**
	 * 打刻日時
	 */
	private GeneralDateTime datetime;

	/**
	 * ボタン位置NO
	 */

	private Integer buttonPositionNo;
	/**
	 * 実績への反映内容
	 */

	private RefectActualResultCommand refActualResults;
}
