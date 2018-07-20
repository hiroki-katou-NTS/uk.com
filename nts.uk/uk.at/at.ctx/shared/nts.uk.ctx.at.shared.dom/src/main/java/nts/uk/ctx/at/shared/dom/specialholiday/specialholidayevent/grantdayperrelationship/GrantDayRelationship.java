package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;

@NoArgsConstructor
@AllArgsConstructor
@Data
/* 続柄に対する上限日数 */
public class GrantDayRelationship {

	private String companyId;

	private int sHolidayEventNo;

	/* 続柄コード */
	private RelationshipCode relationshipCd;

	/* 付与日数 */
	private GrantedDay grantedDay;

	/* 喪主時加算日数 */
	private MorningHour morningHour;
}
