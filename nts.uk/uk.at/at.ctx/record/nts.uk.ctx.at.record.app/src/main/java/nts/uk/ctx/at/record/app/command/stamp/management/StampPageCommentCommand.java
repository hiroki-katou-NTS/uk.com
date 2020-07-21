package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
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
	
	public StampPageComment toDomain() {
		return new StampPageComment(new PageComment(this.pageComment), new ColorCode(this.commentColor));
	}
}
