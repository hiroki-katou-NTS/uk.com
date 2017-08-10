package nts.uk.ctx.at.shared.app.command.relationship;

import lombok.Getter;
import lombok.Setter;

/**
 * insert relationship command
 * 
 * @author yennth
 *
 */
@Getter
@Setter
public class InsertRelationshipCommand {
	/**会社ID**/
	private String companyId;
	/**コード**/
	private String relationshipCd;
	/**名称**/
	private String relationshipName;
}
