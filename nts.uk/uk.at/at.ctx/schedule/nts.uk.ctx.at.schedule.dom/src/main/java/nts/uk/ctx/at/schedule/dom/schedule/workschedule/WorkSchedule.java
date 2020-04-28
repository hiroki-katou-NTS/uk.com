package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.勤務予定
 * @author tutk
 *
 */
@Getter
public class WorkSchedule implements DomainAggregate {
	/**
	 * 社員ID
	 */
	private  final String employeeId;
	
	/**
	 * 年月日ID
	 */
	private  final GeneralDate ymd;

	public WorkSchedule(String employeeId, GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
	}
	
	
	
}
