package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantHolidayTblDeleteCommand {
	/* 年休付与NO */
	private int grantYearHolidayNo;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
}
