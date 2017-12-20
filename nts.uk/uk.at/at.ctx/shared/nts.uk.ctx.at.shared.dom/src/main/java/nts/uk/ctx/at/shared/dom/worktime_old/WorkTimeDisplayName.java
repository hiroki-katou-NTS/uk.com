package nts.uk.ctx.at.shared.dom.worktime_old;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 就業時間帯の表示名
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTimeDisplayName {
	
	private WorkTimeName workTimeName;
	
	private WorkTimeAbName workTimeAbName;
	
	private WorkTimeSymbol workTimeSymbol;

	public WorkTimeDisplayName(WorkTimeName workTimeName, WorkTimeAbName workTimeAbName,
			WorkTimeSymbol workTimeSymbol) {
		super();
		this.workTimeName = workTimeName;
		this.workTimeAbName = workTimeAbName;
		this.workTimeSymbol = workTimeSymbol;
	}
}
