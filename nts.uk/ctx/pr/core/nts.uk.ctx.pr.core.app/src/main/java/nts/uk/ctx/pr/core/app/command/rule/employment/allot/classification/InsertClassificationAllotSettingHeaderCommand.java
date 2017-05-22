package nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeader;

@AllArgsConstructor
@Getter
public class InsertClassificationAllotSettingHeaderCommand {
	String companyCode;
	String historyId;
	YearMonth startDateYM;
	YearMonth endDateYM;
	
	private List<InsertClassificationAllotSettingCommand> insertAllotCommand;
	
	public ClassificationAllotSettingHeader toDomain(String companyCode){
		return ClassificationAllotSettingHeader.createFromJavaType(companyCode, historyId, startDateYM, endDateYM);
	}
	
}
