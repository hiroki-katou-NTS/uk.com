package nts.uk.ctx.at.shared.app.util.attendanceitem;

import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public interface FinderFacade {

	public <T extends ConvertibleAttendanceItem> T find();
}
