package nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.primitives.GrantRelationshipDay;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.primitives.MorningHour;
/**
 * 
 * @author yennth
 *
 */
@Getter
public class GrantRelationship extends AggregateRoot{
	/*会社ID*/
	private String companyId;
	/*コード*/
	private String specialHolidayCode;
	/*コード*/
	private String relationshipCode;
	/* 付与日数 */
	private GrantRelationshipDay grantRelationshipDay;
	/* 喪主時加算日数 */
	private MorningHour morningHour;
	
	public GrantRelationship(String companyId, String specialHolidayCode, String relationshipCode,
			GrantRelationshipDay grantRelationshipDay, MorningHour morningHour) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.relationshipCode = relationshipCode;
		this.grantRelationshipDay = grantRelationshipDay;
		this.morningHour = morningHour;
	}
	
	public static GrantRelationship createFromJavaType(String companyId, String specialHolidayCode, String relationshipCode, int grantRelationshipDay, Integer morningHour){
		return new GrantRelationship(companyId, 
										specialHolidayCode, 
										relationshipCode, 
										new GrantRelationshipDay(grantRelationshipDay), 
										morningHour != null ? new MorningHour(morningHour) : null);
	}
}
