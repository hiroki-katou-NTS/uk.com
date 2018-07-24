package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship;

import lombok.Data;

@Data
public class SaveGrantDayRelationshipCommand {

	/* 続柄コード */
	private String relationshipCd;

	/* 付与日数 */
	private Integer grantedDay;

	/* 喪主時加算日数 */
	private Integer morningHour;

	private boolean createNew;

	private int specialHolidayEventNo;
}
