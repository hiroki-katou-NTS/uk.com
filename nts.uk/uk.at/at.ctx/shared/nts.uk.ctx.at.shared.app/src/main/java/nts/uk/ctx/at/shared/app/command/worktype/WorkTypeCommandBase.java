package nts.uk.ctx.at.shared.app.command.worktype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;


/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeCommandBase {
	private String workTypeCode;
	private String name;
	private String abbreviationName;
	private String symbolicName;
	private int abolishAtr;
	private String memo;
	private int workAtr;
	private String oneDayCls;
	private String morningCls;
	private String afternoonCls;
	private String calculatorMethod;
	private WorkTypeSetBase oneDay;
	private WorkTypeSetBase morning;
    private WorkTypeSetBase afternoon;
	

	public WorkType toDomain(String companyId) {
		WorkType workType = WorkType.createSimpleFromJavaType(companyId, workTypeCode, symbolicName, name,
				abbreviationName, memo, workAtr, Integer.parseInt(oneDayCls), Integer.parseInt(morningCls), Integer.parseInt(afternoonCls), abolishAtr, Integer.parseInt(calculatorMethod));
		return workType;
	}
	
}
