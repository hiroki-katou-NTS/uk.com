package nts.uk.ctx.at.shared.app.command.relationship;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRelationshipCommand {
	/**会社ID**/
	private String companyId;
	/**コード**/
	private String relationshipCode;
	/**名称**/
	private String relationshipName;
}
