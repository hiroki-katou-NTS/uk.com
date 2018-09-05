package nts.uk.ctx.sys.assist.dom.salary;

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
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 基準日
	 */
	private DateSelectClassification refeDate;

	/**
	 * 基準年
	 */
	private YearSelectClassification baseYear;

	/**
	 * 基準月
	 */
	private MonthSelectionSegment baseMonth;

	public IncomTaxBaseYear(String cid, int processCateNo, int refeDate, int baseYear, int baseMonth) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.refeDate = EnumAdaptor.valueOf(refeDate, DateSelectClassification.class);
		this.baseYear = EnumAdaptor.valueOf(baseYear, YearSelectClassification.class);
		this.baseMonth = EnumAdaptor.valueOf(baseMonth, MonthSelectionSegment.class);
	}

}
