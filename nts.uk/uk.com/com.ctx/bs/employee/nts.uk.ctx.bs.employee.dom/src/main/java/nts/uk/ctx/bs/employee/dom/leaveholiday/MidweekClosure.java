package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MidweekClosure {
	/**
	 * 産前休業
	 */

	/** 多胎妊娠区分 */
	private boolean multiple;
	
	/** 出産日 */
	private GeneralDate birthDate;
}
