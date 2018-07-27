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
	/** コード **/
	private String relationshipCode;
	/** 名称 **/
	private String relationshipName;

	private boolean threeParentOrLess;
}
