package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeader;
import nts.uk.shr.com.context.AppContexts;

@Setter
@Getter
public class UpdateClassificationAllotSettingHeaderCommand {

	String companyCode;
	String historyId;
	YearMonth startDateYM;
	YearMonth endDateYM;

	/**
	 * Convert to domain object from command values
	 * 
	 * @return
	 */
	public ClassificationAllotSettingHeader toDomain(String companyCode, String historyId) {
		return ClassificationAllotSettingHeader.createFromJavaType(
				AppContexts.user().companyCode(),
				historyId,
				this.startDateYM,
				this.endDateYM);
	}
}
