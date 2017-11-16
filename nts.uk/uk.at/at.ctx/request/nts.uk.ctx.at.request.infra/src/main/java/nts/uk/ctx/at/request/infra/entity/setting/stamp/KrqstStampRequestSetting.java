package nts.uk.ctx.at.request.infra.entity.setting.stamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
	public String topCommentFontWeight;
	
	@Column(name="BOTTOM_COMMENT_TEXT")
	public String bottomComment;
	
	@Column(name="BOTTOM_COMMENT_FONT_COLOR")
	public String bottomCommentFontColor;
	
	@Column(name="BOTTOM_COMMENT_FONT_WEIGHT")
	public String bottomCommentFontWeight;
	
	@Column(name="RESULT_DISP_FLG")
	public int resultDisp;
	
	@Column(name="SUP_FRAME_DISP_NO")
	public int supFrameDispNO;
	
	@Column(name="STAMP_PLACE_DISP_FLG")
	public int stampPlaceDisp;

	@Column(name="STAMP_ATR_WORK_DISP_FLG")
	public int stampAtr_Work_Disp;
	
	@Column(name="STAMP_ATR_GO_OUT_DISP_FLG")
	public int stampAtr_GoOut_Disp;
	
	@Column(name="STAMP_ATR_CARE_DISP_FLG")
	public int stampAtr_Care_Disp;
	
	@Column(name="STAMP_ATR_SUP_DISP_FLG")
	public int stampAtr_Sup_Disp;
	
	@Column(name="STAMP_OUT_PRI_DISP_FLG")
	public int stampGoOutAtr_Private_Disp;
	
	@Column(name="STAMP_OUT_PUB_DISP_FLG")
	public int stampGoOutAtr_Public_Disp;
	
	@Column(name="STAMP_OUT_COMP_DISP_FLG")
	public int stampGoOutAtr_Compensation_Disp;
	
	@Column(name="STAMP_OUT_UNION_DISP_FLG")
	public int stampGoOutAtr_Union_Disp;

	@Override
	protected Object getKey() {
		return companyID;
	}
}
