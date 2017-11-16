package nts.uk.ctx.at.shared.dom.worktype.worktypedisporder;

import lombok.Getter;

/** 
 * 
 * 勤務種類の並び順
 * @author sonnh
 *
 */
@Getter
public class WorkTypeDispOrder {
	

	/**
	 * The company id
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * The work type code
	 * 勤務種類コード
	 */
	private String workTypeCode;
	

	/**
	 * display order
	 * 並び順
	 */
	private int disporder;

	/**
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param disporder
	 */
	public WorkTypeDispOrder(String companyId, String workTypeCode, int disporder) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.disporder = disporder;
	}

	/**
	 * Create from java type3
	 * 
	 * @param companyId
	 * @param workTypeCode
	 * @param disporder
	 */
	public static WorkTypeDispOrder createFromJavaType(String companyId, String workTypeCode, int disporder) {
		return new WorkTypeDispOrder(companyId, workTypeCode, disporder);
	}
	
	

}
