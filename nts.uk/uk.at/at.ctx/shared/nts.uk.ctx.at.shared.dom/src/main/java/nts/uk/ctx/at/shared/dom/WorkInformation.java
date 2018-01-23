package nts.uk.ctx.at.shared.dom;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 勤務情報
 * @author ken_takasu
 *
 */
@Value
public class WorkInformation {

	private WorkTypeCode workTypeCode;
	private WorkTimeCode siftCode;
	
	
}
