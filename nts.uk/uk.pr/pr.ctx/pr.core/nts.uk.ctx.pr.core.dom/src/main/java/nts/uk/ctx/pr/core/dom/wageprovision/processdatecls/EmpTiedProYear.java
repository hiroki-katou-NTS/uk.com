package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
* 処理年月に紐づく雇用
*/

@Getter
public class EmpTiedProYear extends AggregateRoot
{

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

	public EmpTiedProYear(String cid, int processCateNo, List<EmploymentCode> employmentCodes) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.employmentCodes = employmentCodes;
	}
}
