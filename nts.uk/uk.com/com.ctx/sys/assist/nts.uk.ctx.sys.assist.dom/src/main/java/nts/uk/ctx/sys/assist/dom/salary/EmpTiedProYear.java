package nts.uk.ctx.sys.assist.dom.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 処理年月に紐づく雇用
*/
@AllArgsConstructor
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
	private EmploymentCode employmentCode;
    
    public EmpTiedProYear(String cid, int processCateNo,
			String employmentCode) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.employmentCode = new EmploymentCode(employmentCode);
	}
}
