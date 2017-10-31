package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemJobPosMain extends CtgItemFixDto{
	/** The employeeId 社員ID */
	private String sId;

	/** The jpbPositionId */
	private String jobPositionId;

	/** The JobTitleID */
	private String jobTitleId;
	
	/** Start date*/
	private GeneralDate strD;
	
	/**End date*/
	private GeneralDate endD;
	
	private ItemJobPosMain(String sId, String jobPositionId, String jobTitleId, GeneralDate strD, GeneralDate endD){
		super();
		this.ctgItemType = CtgItemType.JOB_POS_MAIN;
		this.sId = sId;
		this.jobPositionId = jobPositionId;
		this.jobTitleId = jobTitleId;
		this.strD = strD;
		this.endD = endD;
	}
	
	public static ItemJobPosMain createFromJavaType(String sId, String jobPositionId, String jobTitleId, GeneralDate strD, GeneralDate endD){
		return new ItemJobPosMain(sId, jobPositionId, jobTitleId, strD, endD);
	}
	
}
