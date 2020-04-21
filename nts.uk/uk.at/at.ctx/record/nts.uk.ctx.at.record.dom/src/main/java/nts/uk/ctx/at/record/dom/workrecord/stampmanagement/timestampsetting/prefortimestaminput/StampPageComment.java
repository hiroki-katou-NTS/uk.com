package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * ページコメント設定
 * @author phongtq
 *
 */

public class StampPageComment {
	
	/** コメント */
	@Getter
	private PageComment pageComment;
	
	/** コメント色 */
	@Getter
	private ColorCode commentColor;

	public StampPageComment(PageComment pageComment, ColorCode commentColor) {
		this.pageComment = pageComment;
		this.commentColor = commentColor;
	}
}
