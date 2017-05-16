package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeader;

@Value
public class ClassificationAllotSettingHeaderDto {

	String companyCode;
	String historyId;
	YearMonth startDateYM;
	YearMonth endDateYM;
	
	public static ClassificationAllotSettingHeaderDto fromDomain (ClassificationAllotSettingHeader classificationAllotSettingHeader) {
		return new ClassificationAllotSettingHeaderDto (classificationAllotSettingHeader.getCompanyCode().v(),
				classificationAllotSettingHeader.getHistoryId(), classificationAllotSettingHeader.getStartDateYM(),
				classificationAllotSettingHeader.getEndDateYM());
				
	} 
}
