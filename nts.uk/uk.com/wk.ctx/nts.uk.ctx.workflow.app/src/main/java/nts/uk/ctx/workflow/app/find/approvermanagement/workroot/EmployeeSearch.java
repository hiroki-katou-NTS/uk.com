package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class EmployeeSearch  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The workplace codes. */
	private List<String> workplaceIds;
	/** The base date. */
	private GeneralDate baseDate;

}
