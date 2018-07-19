package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*続柄に対する上限日数*/
public class GrantDayRelationship {

	/* 続柄毎の上限日数ID */
	private String grantDayPerRelpId;

	/* 会社ID */
	public String companyId;

	/* 続柄コード */
	public RelationshipCode relationshipCd;

	/* 付与日数 */
	public GrantedDay grantedDay;

	/* 喪主時加算日数 */
	public MorningHour morningHour;
}
