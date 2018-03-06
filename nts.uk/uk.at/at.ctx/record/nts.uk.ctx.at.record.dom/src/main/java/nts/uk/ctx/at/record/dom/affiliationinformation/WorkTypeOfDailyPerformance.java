package nts.uk.ctx.at.record.dom.affiliationinformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;

/**
 * 
 * @author nampt
 * 日別実績の所属情報 - root
 *
 */
@Getter
@NoArgsConstructor
public class WorkTypeOfDailyPerformance extends AggregateRoot{

	private String employeeId;
	
	private GeneralDate date;
	
	/** 勤務種別コード */
	private BusinessTypeCode workTypeCode;

	public WorkTypeOfDailyPerformance(String employeeId, GeneralDate date, BusinessTypeCode workTypeCode) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.workTypeCode = workTypeCode;
	}
	
}
