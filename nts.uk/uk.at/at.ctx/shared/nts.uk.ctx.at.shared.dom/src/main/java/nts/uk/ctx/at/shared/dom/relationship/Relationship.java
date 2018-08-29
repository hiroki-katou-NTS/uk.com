package nts.uk.ctx.at.shared.dom.relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipName;

/**
 * @author yennth
 */
@Getter
@AllArgsConstructor
public class Relationship extends AggregateRoot {
	private String companyId;
	private RelationshipCode relationshipCode;
	private RelationshipName relationshipName;
	private boolean threeParentOrLess;
	private boolean isSetting;

	public static Relationship createFromJavaType(String companyId, String relationshipCode, String relationshipName,
			boolean threeParentOrLess) {
		return new Relationship(companyId, new RelationshipCode(relationshipCode),
				new RelationshipName(relationshipName), threeParentOrLess, false);
	}
}
