package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 社会保険基準年月日
 */
@AllArgsConstructor
@Getter
public class SociInsuStanDate extends DomainObject {

	/**
	 * 基準月
	 */
	private InsuranceStanMonthClassification sociInsuBaseMonth;

	/**
	 * 基準年
	 */
	private YearSelectClassification sociInsuBaseYear;

	/**
	 * 基準日
	 */
	private DateSelectClassification sociInsuRefeDate;

	public SociInsuStanDate(int sociInsuBaseMonth, int sociInsuBaseYear, int sociInsuRefeDate) {
		this.sociInsuBaseMonth = EnumAdaptor.valueOf(sociInsuBaseMonth, InsuranceStanMonthClassification.class);
		this.sociInsuBaseYear = EnumAdaptor.valueOf(sociInsuBaseYear, YearSelectClassification.class);
		this.sociInsuRefeDate = EnumAdaptor.valueOf(sociInsuRefeDate, DateSelectClassification.class);
	}

}
