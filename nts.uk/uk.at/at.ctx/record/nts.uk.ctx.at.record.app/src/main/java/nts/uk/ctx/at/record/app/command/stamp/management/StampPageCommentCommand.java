package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class StampPageCommentCommand {
	/** コメント */
	@Getter
	private String pageComment;
	
	/** コメント色 */
	@Getter
	private String commentColor;

}
