package nts.uk.ctx.at.shared.app.command.grantrelationship;

import lombok.Getter;
import lombok.Setter;
/**
 * update grant relationship command
 * @author yennth
 *
 */
@Getter
@Setter
public class UpdateGrantRelationshipCommand {
	/**コード**/
	private int specialHolidayCode;
	/**コード**/
	private String relationshipCode;
	/** 付与日数 **/
	private int grantRelationshipDay;
	/** 喪主時加算日数 **/
	private Integer morningHour;
}
