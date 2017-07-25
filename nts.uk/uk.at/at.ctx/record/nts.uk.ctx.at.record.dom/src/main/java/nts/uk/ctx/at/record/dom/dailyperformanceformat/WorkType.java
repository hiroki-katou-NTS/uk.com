package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.WorkTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.WorkTypeName;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class WorkType extends AggregateRoot {

	private String companyId;
	
	/**
	 * padding left "0"
	 */
	private WorkTypeCode workTypeCode;
	
	private WorkTypeName workTypeName;

	public WorkType(String companyId, WorkTypeCode workTypeCode, WorkTypeName workTypeName) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.workTypeName = workTypeName;
	}
	
	public static WorkType createFromJavaType(String companyId, String workTypeCode, String workTypeName){
		return new WorkType(companyId, new WorkTypeCode(workTypeCode), new WorkTypeName(workTypeName));
	}	
}
