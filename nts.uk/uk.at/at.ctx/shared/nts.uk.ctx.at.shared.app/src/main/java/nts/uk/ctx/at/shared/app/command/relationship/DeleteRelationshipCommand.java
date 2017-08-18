package nts.uk.ctx.at.shared.app.command.relationship;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author yennth
 * Delete relationship command
 */
@Getter
@Setter
public class DeleteRelationshipCommand {
	/**勤務種別コード**/
	private String relationshipCode;
}
