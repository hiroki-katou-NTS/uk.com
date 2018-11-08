package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class CurrProcessYmExport {

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
	private YearMonth currentYm;
	
	public static CurrProcessYmExport fromDomain(CurrProcessDate domain) {
		return new CurrProcessYmExport(domain.getCid(), domain.getProcessCateNo(), domain.getGiveCurrTreatYear());
	}
	
}
