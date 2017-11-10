package nts.uk.ctx.at.record.dom.workinformation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTimeCode;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTypeCode;

/**
 * 
 * @author nampt
 * 勤務情報
 *
 */
@Getter
public class WorkInformation extends DomainObject{
	
	private WorkTimeCode workTimeCode;
	
	private WorkTypeCode workTypeCode;

	public WorkInformation(String workTimeCode, String workTypeCode) {
		this.workTimeCode = new WorkTimeCode(workTimeCode);
		this.workTypeCode = new WorkTypeCode(workTypeCode);
	}
	
}
