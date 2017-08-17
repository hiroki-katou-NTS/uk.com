package nts.uk.ctx.at.shared.dom.grantrelationship;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.grantrelationship.primitives.GrantRelationshipDay;
import nts.uk.ctx.at.shared.dom.grantrelationship.primitives.MorningHour;

public class GrantRelationship extends AggregateRoot{
	private String companyId;
	private String specialHolidayCd;
	private String relationshipCd;
	private GrantRelationshipDay grantRelationshipDay;
	private MorningHour morningHour;
	public GrantRelationship(String companyId, String specialHolidayCd, String relationshipCd,
			GrantRelationshipDay grantRelationshipDay, MorningHour morningHour) {
		super();
		this.companyId = companyId;
		this.specialHolidayCd = specialHolidayCd;
		this.relationshipCd = relationshipCd;
		this.grantRelationshipDay = grantRelationshipDay;
		this.morningHour = morningHour;
	}
	public static GrantRelationship createFromJavaType(String companyId, String specialHolidayCd, String relationshipCd,
			int grantRelationshipDay, int morningHour){
		return new GrantRelationship(companyId, specialHolidayCd, relationshipCd, new GrantRelationshipDay(grantRelationshipDay), new MorningHour(morningHour));
	}
}
