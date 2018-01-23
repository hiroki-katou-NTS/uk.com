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
	private String specialHolidayCode;
	/** コード */
	private String relationshipCode;
}
