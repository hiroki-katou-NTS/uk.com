package nts.uk.ctx.at.shared.dom.affiliationinformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;

/**
 * 
 * @author nampt
 * 日別実績の勤務種別 - root
 *
 */
@Getter
@NoArgsConstructor
public class WorkTypeOfDailyPerformance extends AggregateRoot{

	private String employeeId;
	
	private GeneralDate date;
	
	/** 勤務種別コード */
	private BusinessTypeCode workTypeCode;

	public WorkTypeOfDailyPerformance(String employeeId, GeneralDate date, String workTypeCode) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = new BusinessTypeCode(workTypeCode);
	}
	
}
