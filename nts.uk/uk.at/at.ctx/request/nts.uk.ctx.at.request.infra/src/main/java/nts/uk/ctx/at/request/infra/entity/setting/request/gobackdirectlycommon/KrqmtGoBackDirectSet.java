package nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name="KRQMT_GO_BACK_DIRECT_SET")
public class KrqmtGoBackDirectSet extends UkJpaEntity{
	@EmbeddedId
	public KrqmtGoBackDirectSetPK krqmtGoBackDirectSetPK;
	
	
	@Column(name="WORK_CHANGE_FLG")
	public int workChangeFlg;
	
	@Column(name="WORK_CHANGE_TIME_ATR")
	public int workChangeTimeAtr;
	
	@Column(name="PERFORMANCE_DISPLAY_ATR")
	public int perfomanceDisplayAtr;
	
	@Column(name="CONTRADITION_CHECK_ATR")
	public int contraditionCheckAtr;
	
	@Column(name="WORK_TYPE")
	public int workType;
	
	@Column(name="LATE_LEAVE_EARLY_SETTING_ATR")
	public int lateLeaveEarlySettingAtr;
	
	@Column(name="COMMENT_CONTENT1")
	public String commentContent1;
	
	@Column(name="COMMENT_FONT_WEIGHT1")
	public int commentFontWeight1;
	
	@Column(name="COMMENT_FONT_COLOR1")
	public String commentFontColor1;
	
	@Column(name="COMMENT_CONTENT2")
	public String commentContent2;
	
	@Column(name="COMMENT_FONT_WEIGHT2")
	public int commentFontWeight2;
	
	@Column(name="COMMENT_FONT_COLOR2")
	public String commentFontColor2;
	
	@Override
	protected Object getKey() {
		return krqmtGoBackDirectSetPK;
	}
}
