package approve.employee.workplace.workplacegroup;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkplaceInforDto {
	//職場コード
	private String workplaceCode;
	//職場名称
	private String workplaceName;
	//職場総称
	private String workplaceGroup;
}
