package nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "KRQST_GO_BACK_DIRECT_SET")
public class KrqstGoBackDirectSet extends ContractUkJpaEntity {
	@EmbeddedId
	public KrqstGoBackDirectSetPK krqstGoBackDirectSetPK;
	/**
	 * 勤務の変更
	 */
	@Column(name = "WORK_CHANGE_FLG")
	public int workChangeFlg;
	/**
	 * 勤務の変更申請時
	 */
	@Column(name = "WORK_CHANGE_TIME_ATR")
	public int workChangeTimeAtr;
	/**
	 * 実績を表示する
	 */
	@Column(name = "PERFORMANCE_DISPLAY_ATR")
	public int perfomanceDisplayAtr;
	/**
	 * 申請対象の矛盾チェック
	 */
	@Column(name = "CONTRADITION_CHECK_ATR")
	public int contraditionCheckAtr;
	/**
	 * 直行直帰申請の勤務種類
	 */
	@Column(name = "WORK_TYPE")
	public int workType;
	/**
	 * 遅刻早退設定
	 */
	@Column(name = "LATE_LEAVE_EARLY_SETTING_ATR")
	public int lateLeaveEarlySettingAtr;
	/**
	 * コメント
	 */
	@Column(name = "COMMENT_CONTENT1")
	public String commentContent1;
	/**
	 * 太字
	 */
	@Column(name = "COMMENT_FONT_WEIGHT1")
	public int commentFontWeight1;
	/**
	 * 文字色
	 */
	@Column(name = "COMMENT_FONT_COLOR1")
	public String commentFontColor1;
	/**
	 * コメント
	 */
	@Column(name = "COMMENT_CONTENT2")
	public String commentContent2;
	/**
	 * 太字
	 */
	@Column(name = "COMMENT_FONT_WEIGHT2")
	public int commentFontWeight2;
	/**
	 * 文字色
	 */
	@Column(name = "COMMENT_FONT_COLOR2")
	public String commentFontColor2;

	@Override
	protected Object getKey() {
		return krqstGoBackDirectSetPK;
	}
}
