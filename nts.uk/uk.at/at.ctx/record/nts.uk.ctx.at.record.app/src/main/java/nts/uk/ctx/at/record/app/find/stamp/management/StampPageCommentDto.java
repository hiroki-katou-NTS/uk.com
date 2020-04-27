package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class StampPageCommentDto {
	/** コメント */
	private String pageComment;
	
	/** コメント色 */ 
	private String commentColor;

	public static StampPageCommentDto fromDomain(StampPageComment pageComment ){
		return new StampPageCommentDto(pageComment.getPageComment().v(), pageComment.getCommentColor().v());
	}
}
