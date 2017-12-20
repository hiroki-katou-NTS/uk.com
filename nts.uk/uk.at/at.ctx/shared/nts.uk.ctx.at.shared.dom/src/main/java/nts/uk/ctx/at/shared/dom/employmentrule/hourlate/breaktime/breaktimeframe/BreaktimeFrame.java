package nts.uk.ctx.at.shared.dom.employmentrule.hourlate.breaktime.breaktimeframe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.FrameNamePri;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * @author loivt
 * 休出枠
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreaktimeFrame {
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 休出枠NO
	 */
	private int breakTimeFrameNo;
	/**
	 * 使用区分
	 */
	private UseAtr useAtr;
	
	/**
	 * 振替枠名称
	 */
	private FrameNamePri transferFrameName;
	/**
	 * 休出枠名称
	 */
	private FrameNamePri breakTimeFrameName;
	
	public static BreaktimeFrame createSimpleFromJavaType(String companyID, int breakTimeFrameNo, int useAtr,String transferFrameName,String breakTimeFrameName ){
		return new BreaktimeFrame(companyID, breakTimeFrameNo, EnumAdaptor.valueOf(useAtr, UseAtr.class), new FrameNamePri(transferFrameName), new FrameNamePri(breakTimeFrameName));
	}
}
