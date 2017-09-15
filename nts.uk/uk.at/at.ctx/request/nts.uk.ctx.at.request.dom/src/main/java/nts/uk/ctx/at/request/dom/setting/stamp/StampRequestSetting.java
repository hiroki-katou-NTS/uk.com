package nts.uk.ctx.at.request.dom.setting.stamp;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * 打刻申請設定
 *
 */
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class StampRequestSetting extends AggregateRoot {
	
	private String companyID;
	
	private String topComment;
	
	private String topCommentFontColor;
	
	private String topCommentFontWeight;
	
	private String bottomComment;
	
	private String bottomCommentFontColor;
	
	private String bottomCommentFontWeight;
	
	private int resultDisp;
	
	private int supFrameDispNO;
	
	private int stampPlaceDisp;

	private int stampAtr_Work_Disp;
	
	private int stampAtr_GoOut_Disp;
	
	private int stampAtr_Care_Disp;
	
	private int stampAtr_Sup_Disp;
	
	private int stampGoOutAtr_Private_Disp;
	
	private int stampGoOutAtr_Public_Disp;
	
	private int stampGoOutAtr_Compensation_Disp;
	
	private int stampGoOutAtr_Union_Disp;

	public StampRequestSetting(String companyID, String topComment, String topCommentFontColor,
			String topCommentFontWeight, String bottomComment, String bottomCommentFontColor,
			String bottomCommentFontWeight, int resultDisp, int supFrameDispNO, int stampPlaceDisp,
			int stampAtr_Work_Disp, int stampAtr_GoOut_Disp, int stampAtr_Care_Disp, int stampAtr_Sup_Disp,
			int stampGoOutAtr_Private_Disp, int stampGoOutAtr_Public_Disp, int stampGoOutAtr_Compensation_Disp,
			int stampGoOutAtr_Union_Disp) {
		super();
		this.companyID = companyID;
		this.topComment = topComment;
		this.topCommentFontColor = topCommentFontColor;
		this.topCommentFontWeight = topCommentFontWeight;
		this.bottomComment = bottomComment;
		this.bottomCommentFontColor = bottomCommentFontColor;
		this.bottomCommentFontWeight = bottomCommentFontWeight;
		this.resultDisp = resultDisp;
		this.supFrameDispNO = supFrameDispNO;
		this.stampPlaceDisp = stampPlaceDisp;
		this.stampAtr_Work_Disp = stampAtr_Work_Disp;
		this.stampAtr_GoOut_Disp = stampAtr_GoOut_Disp;
		this.stampAtr_Care_Disp = stampAtr_Care_Disp;
		this.stampAtr_Sup_Disp = stampAtr_Sup_Disp;
		this.stampGoOutAtr_Private_Disp = stampGoOutAtr_Private_Disp;
		this.stampGoOutAtr_Public_Disp = stampGoOutAtr_Public_Disp;
		this.stampGoOutAtr_Compensation_Disp = stampGoOutAtr_Compensation_Disp;
		this.stampGoOutAtr_Union_Disp = stampGoOutAtr_Union_Disp;
	}
}
