package nts.uk.ctx.basic.dom.organization.department;

import lombok.Getter;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class DepartmentMemo {

	private final String companyCode;

	private final String historyId;

	private Memo memo;

	public DepartmentMemo(String companyCode, String historyId, Memo memo) {
		this.companyCode = companyCode;
		this.historyId = historyId;
		this.memo = memo;
	}

}
