package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AbolitionAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessCls;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class ProcessInformationExport {

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
	private AbolitionAtr deprecatCate;

	/**
	 * 処理区分名称
	 */
	private ProcessCls processCls;

	public static ProcessInformationExport fromDomain(ProcessInformation domain) {
		return new ProcessInformationExport(domain.getCid(), domain.getProcessCateNo(), domain.getDeprecatCate(),
				domain.getProcessCls());
	}

}
