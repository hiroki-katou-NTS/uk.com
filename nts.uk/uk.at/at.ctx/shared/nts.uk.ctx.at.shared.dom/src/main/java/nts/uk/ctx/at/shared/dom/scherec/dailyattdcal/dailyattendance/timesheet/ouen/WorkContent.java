package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;

@Getter
/** 作業内容 */
public class WorkContent implements DomainObject {

	/** 勤務先会社ID: 会社ID */
	private String companyId;
	
	/** 勤務先: 応援別勤務の勤務先 */
	private WorkplaceOfWorkEachOuen workplace;
	
	/** 作業: 作業グループ */
	private Optional<WorkGroup> work;

	private WorkContent(String companyId, WorkplaceOfWorkEachOuen workplace, Optional<WorkGroup> work) {
		super();
		this.companyId = companyId;
		this.workplace = workplace;
		this.work = work;
	}
	
	public static WorkContent create(String companyId, 
			WorkplaceOfWorkEachOuen workplace, Optional<WorkGroup> work) {
		
		return new WorkContent(companyId, workplace, work);
	}
}
