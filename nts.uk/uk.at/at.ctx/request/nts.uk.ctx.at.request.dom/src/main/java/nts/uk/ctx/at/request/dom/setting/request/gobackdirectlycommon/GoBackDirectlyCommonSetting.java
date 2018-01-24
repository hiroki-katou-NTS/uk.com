package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.comment.CommentContent;
import nts.uk.ctx.at.request.dom.setting.request.application.comment.CommentFontColor;
import nts.uk.ctx.at.request.dom.setting.request.application.comment.FontWeightFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.GoBackWorkType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.WorkChangeFlg;
/**
 * 直行直帰申請共通設定
 *
 */
@Value
public class GoBackDirectlyCommonSetting {
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 勤務変更
	 */
	private WorkChangeFlg workChangeFlg;
	/**
	 * 勤務の変更申請時
	 */
	private UseAtr workChangeTimeAtr;
	/**
	 * 実績を表示する
	 */
	private AppDisplayAtr performanceDisplayAtr;
	/**
	 * 申請対象の矛盾チェック
	 */
	private CheckAtr contraditionCheckAtr;
	/**
	 * 直行直帰申請の勤務種類
	 */
	private GoBackWorkType goBackWorkType;
	/**
	 * 遅刻早退設定
	 */
	private CheckAtr lateLeaveEarlySettingAtr;
	/**
	 * コメント
	 */
	private CommentContent commentContent1;
	/**
	 * 太字
	 */
	private FontWeightFlg commentFontWeight1;
	/**
	 * 文字色
	 */
	private CommentFontColor commentFontColor1;
	/**
	 * コメント
	 */
	private CommentContent commentContent2;
	/**
	 * 太字
	 */
	private FontWeightFlg commentFontWeight2;
	/**
	 * 文字色
	 */
	private CommentFontColor commentFontColor2;

	public GoBackDirectlyCommonSetting(String companyID, WorkChangeFlg workChangeFlg,
			UseAtr workChangeTimeAtr, AppDisplayAtr performanceDisplayAtr, CheckAtr contraditionCheckAtr,
			GoBackWorkType goBackWorkType, CheckAtr lateLeaveEarlySettingAtr, CommentContent commentContent1,
			FontWeightFlg commentFontWeight1, CommentFontColor commentFontColor1, CommentContent commentContent2,
			FontWeightFlg commentFontWeight2, CommentFontColor commentFontColor2) {
		super();
		this.companyID = companyID;
		this.workChangeFlg = workChangeFlg;
		this.workChangeTimeAtr = workChangeTimeAtr;
		this.performanceDisplayAtr = performanceDisplayAtr;
		this.contraditionCheckAtr = contraditionCheckAtr;
		this.goBackWorkType = goBackWorkType;
		this.lateLeaveEarlySettingAtr = lateLeaveEarlySettingAtr;
		this.commentContent1 = commentContent1;
		this.commentFontWeight1 = commentFontWeight1;
		this.commentFontColor1 = commentFontColor1;
		this.commentContent2 = commentContent2;
		this.commentFontWeight2 = commentFontWeight2;
		this.commentFontColor2 = commentFontColor2;
	}
	public static GoBackDirectlyCommonSetting createFromJavaType(String companyID, int workChangeFlg,
			int workChangeTimeAtr, int performanceDisplayAtr, int contraditionCheckAtr,
			int goBackWorkType, int lateLeaveEarlySettingAtr, String commentContent1,
			int commentFontWeight1, String commentFontColor1, String commentContent2,
			int commentFontWeight2, String commentFontColor2){
		return new GoBackDirectlyCommonSetting(companyID, EnumAdaptor.valueOf(workChangeFlg, WorkChangeFlg.class),
				EnumAdaptor.valueOf(workChangeTimeAtr, UseAtr.class), 
				EnumAdaptor.valueOf(performanceDisplayAtr, AppDisplayAtr.class),
				EnumAdaptor.valueOf(contraditionCheckAtr, CheckAtr.class),
				EnumAdaptor.valueOf(goBackWorkType, GoBackWorkType.class), 
				EnumAdaptor.valueOf(lateLeaveEarlySettingAtr, CheckAtr.class),
				new CommentContent(commentContent1), 
				EnumAdaptor.valueOf(commentFontWeight1, FontWeightFlg.class),
				new CommentFontColor(commentFontColor1), new CommentContent(commentContent2),
				EnumAdaptor.valueOf(commentFontWeight2, FontWeightFlg.class),
				new CommentFontColor(commentFontColor2));
	}
}
