package nts.uk.ctx.at.shared.app.find.grantrelationship;

import lombok.Value;
/**
 * 
 * @author yennth
 *
 */
@Value
public class GrantRelationshipDto {
	private int specialHolidayCode;
	private String relationshipCode;
	private int grantRelationshipDay;
	private Integer morningHour;
}
