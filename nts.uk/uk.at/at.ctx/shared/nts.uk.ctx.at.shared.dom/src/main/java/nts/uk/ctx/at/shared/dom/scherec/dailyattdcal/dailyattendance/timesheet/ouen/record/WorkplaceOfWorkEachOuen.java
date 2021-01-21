package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

@Getter
/** 応援別勤務の勤務先 */
public class WorkplaceOfWorkEachOuen implements DomainObject {

	/** 職場: 職場ID */
	private String workplaceId;
	
	/** 場所: 勤務場所コード */
	private WorkLocationCD workLocationCD;

	private WorkplaceOfWorkEachOuen(String workplaceId, WorkLocationCD workLocationCD) {
		super();
		this.workplaceId = workplaceId;
		this.workLocationCD = workLocationCD;
	}
	
	public static WorkplaceOfWorkEachOuen create(String workplaceId, WorkLocationCD workLocationCD) {
		
		return new WorkplaceOfWorkEachOuen(workplaceId, workLocationCD);
	}
}
