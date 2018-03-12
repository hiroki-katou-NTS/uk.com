package nts.uk.ctx.at.function.dom.adapter.workplace;

import nts.arc.time.GeneralDate;

public interface WorkplaceAdapter {
	WorkplaceImport getWorlkplaceHistory(String employeeId, GeneralDate baseDate);
}
