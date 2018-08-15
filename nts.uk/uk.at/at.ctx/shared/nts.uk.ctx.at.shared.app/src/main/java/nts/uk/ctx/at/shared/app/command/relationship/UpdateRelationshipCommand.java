package nts.uk.ctx.at.shared.app.command.relationship;

import lombok.Getter;
import lombok.Setter;

/**
 * update relationship command
 * 
 * @author yennth
 *
 */
@Getter
@Setter
public class UpdateRelationshipCommand {
	/** コード **/
	private String relationshipCode;
	/** 名称 **/
	private String relationshipName;

	private boolean threeParentOrLess;

}
