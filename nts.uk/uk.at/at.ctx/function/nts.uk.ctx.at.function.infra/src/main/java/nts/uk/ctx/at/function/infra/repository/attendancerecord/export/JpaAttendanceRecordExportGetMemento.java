package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;

/**
 * The Class JpaAttendanceRecordExportGetMemento.
 */
public class JpaAttendanceRecordExportGetMemento implements AttendanceRecordExportGetMemento {

	/** The entity. */
	private KfnstAttndRec entity;

	/**
	 * Instantiates a new jpa attendance record export get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaAttendanceRecordExportGetMemento(KfnstAttndRec entity) {
		this.entity = entity;
	}

	/**
	 * Instantiates a new jpa attendance record export get memento.
	 */
	JpaAttendanceRecordExportGetMemento() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getExportAtr()
	 */
	@Override
	public ExportAtr getExportAtr() {
		return ExportAtr.valueOf(this.entity.getAttribute().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getColumnIndex()
	 */
	@Override
	public int getColumnIndex() {
		return (int) this.entity.getId().getColumnIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#isUseAtr()
	 */
	@Override
	public Boolean isUseAtr() {
		// ZERO = false, ONE = true
		return this.entity.getUseAtr().compareTo(BigDecimal.ZERO) == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getUpperPosition()
	 */
	@Override
	public Optional<AttendanceRecordDisplay> getUpperPosition() {
		// No Code
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getLowerPosition()
	 */
	@Override
	public Optional<AttendanceRecordDisplay> getLowerPosition() {
		// No Code
		return null;
	}

}
