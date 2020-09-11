package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.shr.com.color.ColorCode;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請コメント設定
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppCommentSet {
	
	/**
	 * コメント
	 */
	private Comment comment;
	
	/**
	 * 太字
	 */
	private boolean bold;
	
	/**
	 * 文字色
	 */
	private ColorCode colorCode;
	
}
