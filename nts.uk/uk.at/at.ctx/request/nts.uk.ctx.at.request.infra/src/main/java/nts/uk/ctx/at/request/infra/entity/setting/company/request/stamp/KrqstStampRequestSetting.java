package nts.uk.ctx.at.request.infra.entity.setting.company.request.stamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppCommentSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.GoOutTypeDisplayControl;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampDisplayControl;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.SupportFrameDispNumber;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="KRQST_STAMP_REQUEST_SET")
public class KrqstStampRequestSetting extends UkJpaEntity {
	
	@Id
	@Column(name="CID")
	public String companyID;
	
	@Column(name="TOP_COMMENT_TEXT")
	public String topComment;
	
	@Column(name="TOP_COMMENT_FONT_COLOR")
	public String topCommentFontColor;
	
	@Column(name="TOP_COMMENT_FONT_WEIGHT")
	public Integer topCommentFontWeight;
	
	@Column(name="BOTTOM_COMMENT_TEXT")
	public String bottomComment;
	
	@Column(name="BOTTOM_COMMENT_FONT_COLOR")
	public String bottomCommentFontColor;
	
	@Column(name="BOTTOM_COMMENT_FONT_WEIGHT")
	public Integer bottomCommentFontWeight;
	
	@Column(name="RESULT_DISP_FLG")
	public Integer resultDisp;
	
	@Column(name="SUP_FRAME_DISP_NO")
	public Integer supFrameDispNO;
	
	@Column(name="STAMP_PLACE_DISP_FLG")
	public Integer stampPlaceDisp;

	@Column(name="STAMP_ATR_WORK_DISP_FLG")
	public Integer stampAtr_Work_Disp;
	
	@Column(name="STAMP_ATR_GO_OUT_DISP_FLG")
	public Integer stampAtr_GoOut_Disp;
	
	@Column(name="STAMP_ATR_CARE_DISP_FLG")
	public Integer stampAtr_Care_Disp;
	
	@Column(name="STAMP_ATR_SUP_DISP_FLG")
	public Integer stampAtr_Sup_Disp;
	
	@Column(name="STAMP_ATR_CHILD_CARE_DISP_FLG")
	public Integer stampAtr_Child_Care_Disp;
	
	@Column(name="STAMP_OUT_PRI_DISP_FLG")
	public Integer stampGoOutAtr_Private_Disp;
	
	@Column(name="STAMP_OUT_PUB_DISP_FLG")
	public Integer stampGoOutAtr_Public_Disp;
	
	@Column(name="STAMP_OUT_COMP_DISP_FLG")
	public Integer stampGoOutAtr_Compensation_Disp;
	
	@Column(name="STAMP_OUT_UNION_DISP_FLG")
	public Integer stampGoOutAtr_Union_Disp;

	@Override
	protected Object getKey() {
		return companyID;
	}
	
	public StampRequestSetting_Old toDomain(){
		return new StampRequestSetting_Old(
				this.companyID, 
				new AppCommentSetting(
						new Comment(this.topComment), 
						this.topCommentFontColor, 
						this.topCommentFontWeight == 1? true : false),
				new AppCommentSetting(
						new Comment(this.bottomComment), 
						this.bottomCommentFontColor, 
						this.bottomCommentFontWeight == 1? true : false),
				EnumAdaptor.valueOf(this.resultDisp, DisplayAtr.class), 
				new SupportFrameDispNumber(this.supFrameDispNO), 
				EnumAdaptor.valueOf(this.stampPlaceDisp, DisplayAtr.class), 
				new StampDisplayControl(
						EnumAdaptor.valueOf(this.stampAtr_Work_Disp, DisplayAtr.class), 
						EnumAdaptor.valueOf(this.stampAtr_GoOut_Disp, DisplayAtr.class), 
						EnumAdaptor.valueOf(this.stampAtr_Care_Disp, DisplayAtr.class), 
						EnumAdaptor.valueOf(this.stampAtr_Sup_Disp, DisplayAtr.class),
						EnumAdaptor.valueOf(this.stampAtr_Child_Care_Disp, DisplayAtr.class)),
				new GoOutTypeDisplayControl(
						EnumAdaptor.valueOf(this.stampGoOutAtr_Private_Disp, DisplayAtr.class), 
						EnumAdaptor.valueOf(this.stampGoOutAtr_Public_Disp, DisplayAtr.class), 
						EnumAdaptor.valueOf(this.stampGoOutAtr_Compensation_Disp, DisplayAtr.class), 
						EnumAdaptor.valueOf(this.stampGoOutAtr_Union_Disp, DisplayAtr.class)));
	}
}
