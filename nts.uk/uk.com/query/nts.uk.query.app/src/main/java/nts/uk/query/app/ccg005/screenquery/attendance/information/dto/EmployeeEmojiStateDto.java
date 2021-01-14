package nts.uk.query.app.ccg005.screenquery.attendance.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiState;

@Builder
@Data
public class EmployeeEmojiStateDto implements EmployeeEmojiState.MementoSetter {
	//年月日
	private GeneralDate date;

	//感情種類
	private Integer emojiType;

	//社員ID
	private String sid;
}
