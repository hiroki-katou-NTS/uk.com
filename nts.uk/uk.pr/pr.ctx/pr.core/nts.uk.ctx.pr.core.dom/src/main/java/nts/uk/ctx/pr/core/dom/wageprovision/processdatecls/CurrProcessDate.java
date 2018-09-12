package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;

/**
 * 現在処理年月
 */

@Getter
public class CurrProcessDate extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 給与現在処理年月
	 */
	private YearMonth giveCurrTreatYear;

	public CurrProcessDate(String cid, int processCateNo, int giveCurrTreatYear) {
		super();
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.giveCurrTreatYear =new YearMonth(giveCurrTreatYear);
	}

}
