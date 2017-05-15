package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;

@AllArgsConstructor
@Getter
public class InsertClassificationAllotSettingHeaderCommand {
	String companyCode;
	String historyId;
	YearMonth startDateYM;
	YearMonth endDateYM;
	
}
