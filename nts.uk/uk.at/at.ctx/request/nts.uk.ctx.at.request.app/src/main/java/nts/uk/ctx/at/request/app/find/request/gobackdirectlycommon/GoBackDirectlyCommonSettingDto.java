package nts.uk.ctx.at.request.app.find.request.gobackdirectlycommon;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 直行直帰申請共通設定 DTO
 */
@AllArgsConstructor
@Value
public class GoBackDirectlyCommonSettingDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 勤務変更
	 */
	private int workChangeFlg;
	/**
	 * 勤務の変更申請時
	 */
	private int workChangeTimeAtr;
	/**
	 * 実績を表示する
	 */
	private int performanceDisplayAtr;
	/**
	 * 申請対象の矛盾チェック
	 */
	private int contraditionCheckAtr;
	/**
	 * 直行直帰申請の勤務種類
	 */
	private int workType;
	/**
	 * 遅刻早退設定
	 */
	private int lateLeaveEarlySetAtr;
	/**
	 * コメント
	 */
	private String commentContent1;
	/**
	 * 太字
	 */
	private int commentFontWeight1;
	/**
	 * 文字色
	 */
	private String commentFontColor1;
	/**
	 * コメント
	 */
	private String commentContent2;
	/**
	 * 太字
	 */
	private int commentFontWeight2;
	/**
	 * 文字色
	 */
	private String commentFontColor2;

}
