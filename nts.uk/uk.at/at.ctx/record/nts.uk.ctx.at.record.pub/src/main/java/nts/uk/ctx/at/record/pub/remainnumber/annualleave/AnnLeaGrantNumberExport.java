package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;

/**
 * 年休付与数
 * @author shuichu_ishida
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnLeaGrantNumberExport {

	/** 付与数 */
	private GrantDays grantDays;
}
