package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import lombok.Data;

@Data
public class EmploymentListCommand {
	/* 会社ID */
	String companyId;

	/* 特別休暇枠NO */
	int specialHolidayEventNo;

	/* 雇用コード */
	String employmentCd;
}
