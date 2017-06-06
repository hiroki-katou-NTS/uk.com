package nts.uk.ctx.at.shared.dom.worktime;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;

/**
 *  就業時間帯の設定
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTime extends AggregateRoot {

	private String companyID;

	private WorkTimeCode workTimeCD;

	private int sortBy;

	private WorkTimeName name;

	private WorkTimeAbName abName;

	private WorkTimeSymbol symbol;

	private String remarks;

	private UseSetting displayAtr;

	private WorkMethodSetting methodAtr;
	
	private WorkTimeSet workTimeSet;

	public WorkTime(String companyID, WorkTimeCode workTimeCD, int sortBy, WorkTimeName name, WorkTimeAbName abName, WorkTimeSymbol symbol,
			String remarks, UseSetting displayAtr, WorkMethodSetting methodAtr, WorkTimeSet workTimeSet) {
		super();
		this.companyID = companyID;
		this.workTimeCD = workTimeCD;
		this.sortBy = sortBy;
		this.name = name;
		this.abName = abName;
		this.symbol = symbol;
		this.remarks = remarks;
		this.displayAtr = displayAtr;
		this.methodAtr = methodAtr;
		this.workTimeSet = workTimeSet;
	}
}
