package nts.uk.ctx.at.request.dom.setting.request.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.setting.request.application.comment.CommentContent;
import nts.uk.ctx.at.request.dom.setting.request.application.comment.CommentFontColor;
import nts.uk.ctx.at.request.dom.setting.request.application.comment.FontWeightFlg;

@AllArgsConstructor
@Getter
/**
 * 勤務変更申請設定
 */
public class AppWorkChangeSet extends AggregateRoot {

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
	private UseAtr workChangeTimeAtr;

	/**
	 * 実績を表示する
	 */
	private int displayResultAtr;

	/**
	 * 勤務時間の初期表示
	 */
	private InitDisplayWorktimeAtr initDisplayWorktime;

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

	public static AppWorkChangeSet createFromJavaType(String cid, int excludeHoliday, int workChangeTimeAtr,
			int displayResultAtr, int initDisplayWorktime, String commentContent1, int commentFontWeight1,
			String commentFontColor1, String commentContent2, int commentFontWeight2, String commentFontColor2) {
		return new AppWorkChangeSet(cid, excludeHoliday, 
				EnumAdaptor.valueOf(workChangeTimeAtr, UseAtr.class), displayResultAtr,
				EnumAdaptor.valueOf(initDisplayWorktime, InitDisplayWorktimeAtr.class),
				commentContent1 == null ? null : new CommentContent(commentContent1), 
				EnumAdaptor.valueOf(commentFontWeight1, FontWeightFlg.class),
				new CommentFontColor(commentFontColor1), 
				commentContent2 == null ? null : new CommentContent(commentContent2),
				EnumAdaptor.valueOf(commentFontWeight2, FontWeightFlg.class), new CommentFontColor(commentFontColor2));
	}

}
