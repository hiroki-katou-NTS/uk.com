package nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * @author loivt
 * 残業枠
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	public static OvertimeFrame createSimpleFromJavaType(String companyID, int otFrameNo, int useAtr,String transferFrameName,String overtimeFrameName ){
		return new OvertimeFrame(companyID, otFrameNo, EnumAdaptor.valueOf(useAtr, UseAtr.class), new FrameNamePri(transferFrameName), new FrameNamePri(overtimeFrameName));
	}

}
