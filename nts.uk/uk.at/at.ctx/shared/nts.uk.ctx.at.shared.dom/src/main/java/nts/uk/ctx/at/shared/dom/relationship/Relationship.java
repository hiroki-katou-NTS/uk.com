package nts.uk.ctx.at.shared.dom.relationship;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipName;
/**
 * @author yennth
 */
@Getter
public class Relationship extends AggregateRoot{
	private String companyId;
	private RelationshipCode relationshipCode;
	private RelationshipName relationshipName;
	
	public Relationship(String companyId, RelationshipCode relationshipCode, RelationshipName relationshipName) {
		super();
		this.companyId = companyId;
		this.relationshipCode = relationshipCode;
		this.relationshipName = relationshipName;
	}
	public static Relationship createFromJavaType(String companyId, String relationshipCode, String relationshipName){
		return new Relationship(companyId, new RelationshipCode(relationshipCode), new RelationshipName(relationshipName));
	}
}
