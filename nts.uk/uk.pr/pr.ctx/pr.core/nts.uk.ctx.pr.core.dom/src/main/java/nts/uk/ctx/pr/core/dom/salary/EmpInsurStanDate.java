package nts.uk.ctx.pr.core.dom.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 雇用保険基準日
 */
@AllArgsConstructor
@Getter
public class EmpInsurStanDate extends DomainObject {

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
	 * 基準月
	 */
	private MonthSelectionSegment baseMonth;

	public EmpInsurStanDate(String cid, int processCateNo, int refeDate, int baseMonth) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.refeDate = EnumAdaptor.valueOf(refeDate, DateSelectClassification.class);
		this.baseMonth = EnumAdaptor.valueOf(baseMonth, MonthSelectionSegment.class);
	}

}
