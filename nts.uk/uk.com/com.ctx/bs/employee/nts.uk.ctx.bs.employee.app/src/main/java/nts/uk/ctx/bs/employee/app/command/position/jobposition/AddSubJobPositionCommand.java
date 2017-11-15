package nts.uk.ctx.bs.employee.app.command.position.jobposition;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSubJobPositionCommand {
	
	/**職務職位ID（兼務） sub job position id*/
	private String subJobPosId;
	
	/**所属部門ID affiliation department id*/
	@PeregItem("")
	private String affiDeptId;
	
	/**役職ID job title id*/
	@PeregItem("")
	private String jobTitleId;
	
	/**Start date*/
	@PeregItem("")
	private GeneralDate startDate;
	
	/**End date*/
	@PeregItem("")
	private GeneralDate endDate;
}
