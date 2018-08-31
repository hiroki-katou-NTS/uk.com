package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteGrantDayRelationshipCommand {
	/* 続柄コード */
	private String relationshipCd;

	private int specialHolidayEventNo;
}
