package nts.uk.ctx.at.record.app.command.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class ConfirmUseOfStampInputResult {
	private GeneralDateTime systemDate;

	// 打刻利用可否
	private int used;
}
