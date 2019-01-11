package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import lombok.Value;
import nts.arc.time.YearMonth;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class CurrProcessYmImport {

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
	
}
