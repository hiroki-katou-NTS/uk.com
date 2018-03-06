package nts.uk.ctx.at.function.dom.adapter.workplace;

import nts.arc.time.GeneralDate;

public interface FuncWorkplaceAdapter {
	FuncWorkplaceImport getWorlkplaceHistory(String employeeId, GeneralDate baseDate);
}
