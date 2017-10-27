package nts.uk.ctx.at.shared.app.command.specialholiday.grantrelationship;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author yennth
 * Delete grant relationship command
 */
@Getter
@Setter
public class DeleteGrantRelationshipCommand {
	/** コード */
	private int specialHolidayCode;
	/** コード */
	private String relationshipCode;
}
