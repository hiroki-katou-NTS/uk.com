package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;

/**
 * 申請コメント設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppCommentSetting {
	
	/**
	 * コメント
	 */
	private Comment comment;
	
	/**
	 * 文字色
	 */
	private String fontColor;
	
	/**
	 * 太字
	 */
	private Boolean fontWeight;

//	private AppCommentSetting aaaa(String comment, String fontColor, Boolean fontWeight) {
//		return new AppCommentSetting(new Comment(comment), fontColor, fontWeight);
//	}
	
}
