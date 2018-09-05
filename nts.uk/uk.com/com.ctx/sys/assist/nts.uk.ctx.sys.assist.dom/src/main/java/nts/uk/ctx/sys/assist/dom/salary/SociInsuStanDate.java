package nts.uk.ctx.sys.assist.dom.salary;

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
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 基準月
	 */
	private InsuranceStanMonthClassification baseMonth;

	/**
	 * 基準年
	 */
	private YearSelectClassification baseYear;

	/**
	 * 基準日
	 */
	private DateSelectClassification refeDate;
	
	public SociInsuStanDate(String cid, int processCateNo, int baseMonth, int baseYear, int refeDate) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.baseMonth = EnumAdaptor.valueOf(baseMonth, InsuranceStanMonthClassification.class);
		this.baseYear = EnumAdaptor.valueOf(baseYear, YearSelectClassification.class);
		this.refeDate = EnumAdaptor.valueOf(refeDate, DateSelectClassification.class);
	}

}
