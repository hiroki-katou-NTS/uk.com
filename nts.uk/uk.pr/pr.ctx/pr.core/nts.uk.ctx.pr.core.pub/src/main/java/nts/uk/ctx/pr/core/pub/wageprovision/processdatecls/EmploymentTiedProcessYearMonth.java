package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmploymentCode;

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
	private List<EmploymentCode> employmentCodes;

	public static EmploymentTiedProcessYearMonth fromDomain(EmpTiedProYear domain) {
		return new EmploymentTiedProcessYearMonth(domain.getCid(), domain.getProcessCateNo(),
				domain.getEmploymentCodes());
	}

}
