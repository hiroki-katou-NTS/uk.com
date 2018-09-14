package nts.uk.ctx.at.shared.dom.scherec.service;

import nts.uk.ctx.at.shared.dom.scherec.event.OptionalItemAtrExport;

public interface AttendanceAtrService {
	/**
	 * 任意項目の属性を更新する
	 */
	public void updateAttendanceAtr(OptionalItemAtrExport domainEvent);
}
