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
	private RelationshipCode relationshipCd;
	private RelationshipName relationshipName;
	
	public Relationship(String companyId, RelationshipCode relationshipCd, RelationshipName relationshipName) {
		super();
		this.companyId = companyId;
		this.relationshipCd = relationshipCd;
		this.relationshipName = relationshipName;
	}
	public static Relationship createFromJavaType(String companyId, String relationshipCd, String relationshipName){
		return new Relationship(companyId, new RelationshipCode(relationshipCd), new RelationshipName(relationshipName));
	}
}
