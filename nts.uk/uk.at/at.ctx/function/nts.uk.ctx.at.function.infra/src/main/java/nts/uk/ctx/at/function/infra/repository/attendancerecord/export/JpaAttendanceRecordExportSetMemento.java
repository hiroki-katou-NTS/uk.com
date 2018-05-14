package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;

public class JpaAttendanceRecordExportSetMemento implements AttendanceRecordExportSetMemento {

	private KfnstAttndRec entity;

	public JpaAttendanceRecordExportSetMemento() {

	}

	public JpaAttendanceRecordExportSetMemento(KfnstAttndRec entity) {
		this.entity = entity;
	}

	@Override
	public void setExportClassification(ExportAtr exportAtr) {
		this.entity.setAttribute(new BigDecimal(exportAtr.value));

	}

	@Override
	public void setColumnIndex(int index) {
		this.entity.getId().setColumnIndex(index);

	}

	@Override
	public void isUseAtr(Boolean isUseAtr) {

		this.entity.setUseAtr(isUseAtr ? BigDecimal.ONE : BigDecimal.ZERO);

	}

	@Override
	public void setUpperPosition(Optional<AttendanceRecordDisplay> attendanceRecDisplay) {
		// No code

	}

	@Override
	public void setLowerPosition(Optional<AttendanceRecordDisplay> attendanceRecDisplay) {
		// No code

	}

}
