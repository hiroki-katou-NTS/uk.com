package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class EmploymentTiedProcessYearMonth {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 雇用コード
	 */
	private List<String> employmentCodes;

}
