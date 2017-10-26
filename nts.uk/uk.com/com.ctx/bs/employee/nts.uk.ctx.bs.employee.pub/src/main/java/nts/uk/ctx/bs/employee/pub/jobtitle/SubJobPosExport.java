package nts.uk.ctx.bs.employee.pub.jobtitle;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class SubJobPosExport {
	/**職務職位ID（兼務） sub job position id*/
	private String subJobPosId;
	/**所属部門ID affiliation department id*/
	private String affiDeptId;
	/**役職ID job title id*/
	private String jobTitleId;
	/**Start date*/
	private GeneralDate startDate;
	/**End date*/
	private GeneralDate endDate;
}
