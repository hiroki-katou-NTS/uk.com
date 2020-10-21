package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;

/**
 * @author thanh_nx
 *
 *         振休振出として扱う日数
 */
@AllArgsConstructor
@Data
public class NumberOfDaySuspension {

	//振休振出日数
	private UsedDays days;

	//振休振出区分
	private FuriClassifi classifiction;

}
