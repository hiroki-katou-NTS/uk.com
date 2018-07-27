package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.grantdayperrelationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;

@Data
@AllArgsConstructor
public class GrantDayRelationshipDto {

	/* 続柄コード */
	public String relationshipCd;

	/* 付与日数 */
	public Integer grantedDay;

	/* 喪主時加算日数 */
	public Integer morningHour;

	public static GrantDayRelationshipDto fromDomain(GrantDayRelationship domain) {
		return new GrantDayRelationshipDto( domain.getRelationshipCd().v(),
				domain.getGrantedDay().v(), domain.getMorningHour().v());
	}

}
