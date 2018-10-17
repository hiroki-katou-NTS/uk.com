package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class ProcessInformationImport {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 廃止区分
	 */
	private int deprecatCate;

	/**
	 * 処理区分名称
	 */
	private String processClsName;
	
}
