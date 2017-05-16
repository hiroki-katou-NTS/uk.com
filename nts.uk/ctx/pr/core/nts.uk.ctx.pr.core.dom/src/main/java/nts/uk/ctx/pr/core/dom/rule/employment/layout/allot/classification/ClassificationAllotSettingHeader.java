package nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
@AllArgsConstructor
public class ClassificationAllotSettingHeader extends AggregateRoot {
	@Getter
	private final CompanyCode companyCode;

	@Getter
	private String historyId;

	@Getter
	private YearMonth startDateYM;

	@Getter
	private YearMonth endDateYM;
	
	
//	private List<ClassificationAllotSetting> classSetting;


	public static ClassificationAllotSettingHeader createFromJavaType(String companyCode, String historyId,
			YearMonth startDateYM, YearMonth endDateYM) {
		return new ClassificationAllotSettingHeader(new CompanyCode(companyCode), historyId, startDateYM, endDateYM);
	}
}
