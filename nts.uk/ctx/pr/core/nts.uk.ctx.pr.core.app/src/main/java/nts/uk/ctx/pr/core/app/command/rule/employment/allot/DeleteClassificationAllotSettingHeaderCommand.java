package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;

@AllArgsConstructor
@Getter
public class DeleteClassificationAllotSettingHeaderCommand {
	String companyCode;
	String historyId;
	YearMonth startDateYM;
	YearMonth endDateYM;

	/**
	 * Convert to domain object from command values
	 * 
	 * @return
	 */
	public DeleteClassificationAllotSettingHeaderCommand toDomain(String companyCode, String historyId,
			YearMonth startDateYM, YearMonth endDateYM) {
		return new DeleteClassificationAllotSettingHeaderCommand(AppContexts.user().companyCode(), historyId,
				startDateYM, endDateYM);
	}
}
