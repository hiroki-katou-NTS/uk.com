package nts.uk.ctx.at.shared.dom.worktype.worktypedisporder;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Getter
public class WorkTypeDisporder {
	
	/** The company id. */
	/*会社ID*/
	private String companyId;
	
	/** The work type code. */
	/*勤務種類コード*/
	private String workTypeCode;
	
	/*並び順*/
	private int disporder;

	/**
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param disporder
	 */
	public WorkTypeDisporder(String companyId, String workTypeCode, int disporder) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.disporder = disporder;
	}
	
	

}
