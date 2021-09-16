package nts.uk.ctx.at.shared.app.service.worktype;

import nts.uk.ctx.at.shared.app.command.worktype.WorkTypeCommandBase;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface WorkTypeService {
	boolean isExistingCloseAtr(WorkType workType, WorkTypeCommandBase workTypeCommandBase);
}
