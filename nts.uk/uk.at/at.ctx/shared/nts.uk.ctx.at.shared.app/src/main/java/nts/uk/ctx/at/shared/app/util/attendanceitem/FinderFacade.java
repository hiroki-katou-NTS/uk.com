package nts.uk.ctx.at.shared.app.util.attendanceitem;

import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public abstract class FinderFacade {

	public abstract <T extends ConvertibleAttendanceItem> T find();
}
