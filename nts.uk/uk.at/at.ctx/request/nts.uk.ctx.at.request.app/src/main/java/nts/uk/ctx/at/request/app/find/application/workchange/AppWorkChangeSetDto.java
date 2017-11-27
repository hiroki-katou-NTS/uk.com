package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;

/**
 * 勤務変更申請設定
 */
@Value
public class AppWorkChangeSetDto {

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

	public static AppWorkChangeSetDto fromDomain(AppWorkChangeSet domain) {
		return new AppWorkChangeSetDto(domain.getCid(), domain.getExcludeHoliday(), domain.getWorkChangeTimeAtr(),
				domain.getDisplayResultAtr(), domain.getInitDisplayWorktime().value, domain.getCommentContent1().v(),
				domain.getCommentFontWeight1().value, domain.getCommentFontColor1().v(),
				domain.getCommentContent2().v(), domain.getCommentFontWeight2().value,
				domain.getCommentFontColor2().v());
	}

}
