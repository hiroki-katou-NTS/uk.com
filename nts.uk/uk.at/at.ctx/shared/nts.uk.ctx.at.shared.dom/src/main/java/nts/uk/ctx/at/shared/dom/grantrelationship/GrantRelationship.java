package nts.uk.ctx.at.shared.dom.grantrelationship;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.grantrelationship.primitives.GrantRelationshipDay;
import nts.uk.ctx.at.shared.dom.grantrelationship.primitives.MorningHour;
/**
 * 
 * @author yennth
 *
 */
@Getter
public class GrantRelationship extends AggregateRoot{
	private String companyId;
	private int specialHolidayCode;
	private String relationshipCode;
	private GrantRelationshipDay grantRelationshipDay;
	private MorningHour morningHour;
	public GrantRelationship(String companyId, int specialHolidayCode, String relationshipCode,
			GrantRelationshipDay grantRelationshipDay, MorningHour morningHour) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.relationshipCode = relationshipCode;
		this.grantRelationshipDay = grantRelationshipDay;
		this.morningHour = morningHour;
	}
	public static GrantRelationship createFromJavaType(String companyId, int specialHolidayCode, String relationshipCode,
			int grantRelationshipDay, int morningHour){
		return new GrantRelationship(companyId, specialHolidayCode, relationshipCode, new GrantRelationshipDay(grantRelationshipDay), new MorningHour(morningHour));
	}
}
