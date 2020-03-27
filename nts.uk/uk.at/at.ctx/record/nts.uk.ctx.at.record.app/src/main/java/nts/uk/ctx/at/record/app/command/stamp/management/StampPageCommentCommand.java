package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class StampPageCommentCommand {
	/** コメント */
	private String pageComment;
	
	/** コメント色 */
	private String commentColor;

	public StampPageCommentCommand(String pageComment, String commentColor) {
		this.pageComment = pageComment;
		this.commentColor = commentColor;
	}
}
