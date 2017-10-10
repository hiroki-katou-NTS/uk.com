package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class MidweekClosure {

	/** 多胎妊娠区分 */
	private boolean multiple;
	
	/** 出産日 */
	private GeneralDate birthDate;
}
