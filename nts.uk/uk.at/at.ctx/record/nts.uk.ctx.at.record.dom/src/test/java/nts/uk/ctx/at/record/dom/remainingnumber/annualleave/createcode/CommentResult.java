package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import lombok.Getter;
import lombok.Setter;

/**
 * 複数行コメント解析結果
 * @author jinno
 *
 */
@Getter
@Setter
public class CommentResult {

	/**
	 * コメント前の文字列
	 */
	private String beforeComment = "";
	
	/**
	 * コメント
	 */
	private String comment = "";
	
	/**
	 * コメント後の文字列
	 */
	private String afterComment = "";
	
}
