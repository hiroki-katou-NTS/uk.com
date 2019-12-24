package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class EmploymentTiedProcYmImport {

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
