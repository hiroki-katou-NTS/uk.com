package nts.uk.ctx.at.request.app.command.setting.company.request.stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;

/**
 * Stamp Request Setting Command
 * @author yennth
 */
@Data
@AllArgsConstructor
public class StampRequestSettingCommand {
	public String topComment;
	
	public String topCommentFontColor;
	
	public Boolean topCommentFontWeight;
	
	public String bottomComment;
	
	public String bottomCommentFontColor;
	
	public Boolean bottomCommentFontWeight;
	
	public Integer resultDisp;
	
	public Integer supFrameDispNO;
	
	public Integer stampAtr_Work_Disp;
	
	public Integer stampAtr_GoOut_Disp;
	
	public Integer stampAtr_Care_Disp;
	
	public Integer stampAtr_Sup_Disp;
	
	public Integer stampAtr_Child_Care_Disp;
	
	public Integer stampGoOutAtr_Private_Disp;
	
	public Integer stampGoOutAtr_Public_Disp;
	
	public Integer stampGoOutAtr_Compensation_Disp;
	
	public Integer stampGoOutAtr_Union_Disp;
	
	public StampRequestSetting_Old toDomain(String companyId){
		StampRequestSetting_Old stampRequest = StampRequestSetting_Old.createFromJavaType(companyId, this.getTopComment(), 
								this.getTopCommentFontColor(), this.getTopCommentFontWeight(), this.getBottomComment(), 
								this.getBottomCommentFontColor(), this.getBottomCommentFontWeight(), this.getResultDisp(), 
								this.getSupFrameDispNO(), 0, this.getStampAtr_Work_Disp(), 
								this.getStampAtr_GoOut_Disp(), this.getStampAtr_Care_Disp(), this.getStampAtr_Sup_Disp(),
								this.getStampAtr_Child_Care_Disp(),
								this.getStampGoOutAtr_Private_Disp(), this.getStampGoOutAtr_Public_Disp(), 
								this.getStampGoOutAtr_Compensation_Disp(), this.getStampGoOutAtr_Union_Disp());
		return stampRequest;
	}
}
