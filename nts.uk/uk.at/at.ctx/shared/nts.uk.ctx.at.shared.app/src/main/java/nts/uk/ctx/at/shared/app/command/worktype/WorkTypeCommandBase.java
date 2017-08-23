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
	private String symbolicName;
	private String name;
	private String abbreviationName;
	private String memo;
	private int workTypeUnit;
	private int oneDay;
	private int morning;
	private int afternoon;
	private int deprecate;
	private int calculateMethod;

	public WorkType toDomain(String companyId) {
		WorkType workType = WorkType.createSimpleFromJavaType(companyId, workTypeCode, symbolicName, name,
				abbreviationName, memo, workTypeUnit, oneDay, morning, afternoon, deprecate, calculateMethod);
		return workType;
	}
}
