package nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto;

import lombok.AllArgsConstructor;

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
	
	public Integer stampGoOutAtr_Private_Disp;
	
	public Integer stampGoOutAtr_Public_Disp;
	
	public Integer stampGoOutAtr_Compensation_Disp;
	
	public Integer stampGoOutAtr_Union_Disp;
	
}
