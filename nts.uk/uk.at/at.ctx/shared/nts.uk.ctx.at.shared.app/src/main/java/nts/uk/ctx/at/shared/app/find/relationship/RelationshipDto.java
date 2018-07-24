package nts.uk.ctx.at.shared.app.find.relationship;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class RelationshipDto {

	private String relationshipCode;
	private String relationshipName;
	private int threeParentOrLess;
	private boolean setting;

	public static RelationshipDto fromJavaType(String Cd, String name, int threeParentOrLess) {

		return new RelationshipDto(Cd, name, threeParentOrLess, false);

	}
}
