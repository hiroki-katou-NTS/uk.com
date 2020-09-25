package nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class StampRequestSettingDto {
	
	public String companyID;
	
	public String topComment;
	
	public String topCommentFontColor;
	
	public Boolean topCommentFontWeight;
	
	public String bottomComment;
	
	public String bottomCommentFontColor;
	
	public Boolean bottomCommentFontWeight;
	
	public Integer resultDisp;
	
	public Integer supFrameDispNO;
	
	public Integer stampPlaceDisp;

	public Integer stampAtr_Work_Disp;
	
	public Integer stampAtr_GoOut_Disp;
	
	public Integer stampAtr_Care_Disp;
	
	public Integer stampAtr_Sup_Disp;
	
	public Integer stampAtr_Child_Care_Disp;
	
	public Integer stampGoOutAtr_Private_Disp;
	
	public Integer stampGoOutAtr_Public_Disp;
	
	public Integer stampGoOutAtr_Compensation_Disp;
	
	public Integer stampGoOutAtr_Union_Disp;
	
	public static StampRequestSettingDto fromDomain(StampRequestSetting_Old stampRequestSetting){
		return new StampRequestSettingDto(
				stampRequestSetting.getCompanyID(), 
				stampRequestSetting.getTopComment().getComment().v(), 
				stampRequestSetting.getTopComment().getFontColor(), 
				stampRequestSetting.getTopComment().getFontWeight(), 
				stampRequestSetting.getBottomComment().getComment().v(), 
				stampRequestSetting.getBottomComment().getFontColor(), 
				stampRequestSetting.getBottomComment().getFontWeight(), 
				stampRequestSetting.getResultDisp().value, 
				stampRequestSetting.getSupFrameDispNO().v(), 
				stampRequestSetting.getStampPlaceDisp().value, 
				stampRequestSetting.getStampDisplayControl().getStampAtrWorkDisp().value, 
				stampRequestSetting.getStampDisplayControl().getStampAtrGoOutDisp().value, 
				stampRequestSetting.getStampDisplayControl().getStampAtrCareDisp().value, 
				stampRequestSetting.getStampDisplayControl().getStampAtrSupDisp().value,
				stampRequestSetting.getStampDisplayControl().getStampAtrChildCareDisp().value, 
				stampRequestSetting.getGoOutTypeDisplayControl().getStampGoOutAtrPrivateDisp().value, 
				stampRequestSetting.getGoOutTypeDisplayControl().getStampGoOutAtrPublicDisp().value, 
				stampRequestSetting.getGoOutTypeDisplayControl().getStampGoOutAtrCompensationDisp().value, 
				stampRequestSetting.getGoOutTypeDisplayControl().getStampGoOutAtrUnionDisp().value);
	}
	
}
