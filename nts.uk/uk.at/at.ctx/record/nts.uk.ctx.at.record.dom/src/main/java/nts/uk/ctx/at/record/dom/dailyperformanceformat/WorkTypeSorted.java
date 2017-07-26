package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class WorkTypeSorted extends AggregateRoot {

	private String companyId;

	private String attendanceItemId;
	
	private BigDecimal order;

	public WorkTypeSorted(String companyId, String attendanceItemId, BigDecimal order) {
		super();
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.order = order;
	}
	
	public static WorkTypeSorted createFromJavaType(String companyId, String attendanceItemId, BigDecimal order){
		return new WorkTypeSorted(companyId, attendanceItemId, order);
	}
}
