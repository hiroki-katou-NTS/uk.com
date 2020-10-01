package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet_Old;

/**
 * 勤務変更申請設定
 */
@Value
public class AppWorkChangeSetDto_Old {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * スケジュールが休日の場合は除くを表示する
	 */
	private int excludeHoliday;

	/**
	 * 勤務時間を変更できる
	 */
	private int workChangeTimeAtr;

	/**
	 * 実績を表示する
	 */
	private int displayResultAtr;

	/**
	 * 勤務時間の初期表示
	 */
	private int initDisplayWorktime;

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

	public static AppWorkChangeSetDto_Old fromDomain(AppWorkChangeSet_Old domain) {
		return new AppWorkChangeSetDto_Old(domain.getCid(), domain.getExcludeHoliday(), domain.getWorkChangeTimeAtr().value,
				domain.getDisplayResultAtr(), domain.getInitDisplayWorktime().value, 
				domain.getCommentContent1() != null ? domain.getCommentContent1().v() : null,
				domain.getCommentFontWeight1() != null ? domain.getCommentFontWeight1().value : null, 
				domain.getCommentFontColor1() != null ? domain.getCommentFontColor1().v() : null,
				domain.getCommentContent2() != null ? domain.getCommentContent2().v() : null,
				domain.getCommentFontWeight2() != null ? domain.getCommentFontWeight2().value : null,
				domain.getCommentFontColor2() != null ? domain.getCommentFontColor2().v() : null);
	}
	
	public AppWorkChangeSet_Old toDomain() {
		return AppWorkChangeSet_Old.createFromJavaType(
				cid, 
				excludeHoliday, 
				workChangeTimeAtr, 
				displayResultAtr, 
				initDisplayWorktime, 
				commentContent1, 
				commentFontWeight1, 
				commentFontColor1, 
				commentContent2, 
				commentFontWeight2, 
				commentFontColor2); 
	}

}
