package nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

@Data
public class OvertimeFrame {
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 残業枠NO
	 */
	private int otFrameNo;
	/**
	 * 使用区分
	 */
	private UseAtr useAtr;
	
	/**
	 * 振替枠名称
	 */
	private FrameNamePri transferFrameName;
	/**
	 * 残業枠名称
	 */
	private FrameNamePri overtimeFrameName;

}
