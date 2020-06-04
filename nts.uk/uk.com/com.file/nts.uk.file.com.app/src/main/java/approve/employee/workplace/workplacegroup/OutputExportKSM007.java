package approve.employee.workplace.workplacegroup;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputExportKSM007 {
	
	private CreateUnsetWorkplaceHeader header;

	private List<WorkplaceInforDto> listWorkplaceInfor;
}
