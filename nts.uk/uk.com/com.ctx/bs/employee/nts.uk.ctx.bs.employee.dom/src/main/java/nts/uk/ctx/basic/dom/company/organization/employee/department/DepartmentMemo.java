/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.Memo;


/**
 * The Class DepartmentMemo.
 */
@Getter
public class DepartmentMemo extends AggregateRoot{

	/** The company code. */
	private final String companyCode;

	/** The history id. */
	private final String historyId;

	/** The memo. */
	private Memo memo;

	/**
	 * Instantiates a new department memo.
	 *
	 * @param companyCode the company code
	 * @param historyId the history id
	 * @param memo the memo
	 */
	public DepartmentMemo(String companyCode, String historyId, Memo memo) {
		this.companyCode = companyCode;
		this.historyId = historyId;
		this.memo = memo;
	}

	/**
	 * Creates the from java type.
	 *
	 * @param companyCode the company code
	 * @param historyId the history id
	 * @param memo the memo
	 * @return the department memo
	 */
	public static DepartmentMemo createFromJavaType(String companyCode, String historyId, String memo) {
		return new DepartmentMemo(companyCode, companyCode, new Memo(memo));
	}
}
