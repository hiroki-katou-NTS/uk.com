package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 所得税基準年月日
 */
@AllArgsConstructor
@Getter
public class IncomTaxBaseYear extends DomainObject {

	/**
	 * 基準日
	 */
	private DateSelectClassification inComRefeDate;

	/**
	 * 基準年
	 */
	private YearSelectClassification inComBaseYear;

	/**
	 * 基準月
	 */
	private MonthSelectionSegment inComBaseMonth;

	public IncomTaxBaseYear(int inComRefeDate, int inComBaseYear, int inComBaseMonth) {
		this.inComRefeDate = EnumAdaptor.valueOf(inComRefeDate, DateSelectClassification.class);
		this.inComBaseYear = EnumAdaptor.valueOf(inComBaseYear, YearSelectClassification.class);
		this.inComBaseMonth = EnumAdaptor.valueOf(inComBaseMonth, MonthSelectionSegment.class);
	}

}
