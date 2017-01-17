package nts.uk.ctx.basic.dom.organization.workplace;

import lombok.Getter;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class WorkPlaceMemo {

	private final String companyCode;

	private final String historyId;

	private Memo memo;

	public WorkPlaceMemo(String companyCode, String historyId, Memo memo) {
		this.companyCode = companyCode;
		this.historyId = historyId;
		this.memo = memo;
	}

}
