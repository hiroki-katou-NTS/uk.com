package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;

/**
 * The Class JpaAttendanceRecordExportGetMemento.
 */
public class JpaAttendanceRecordExportGetMemento implements AttendanceRecordExportGetMemento {

	/** The entity. */
	private KfnstAttndRec upperEntity;

	private KfnstAttndRec lowerEntity;

	/**
	 * Instantiates a new jpa attendance record export get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaAttendanceRecordExportGetMemento(KfnstAttndRec upperEntity, KfnstAttndRec lowerEntity) {
		this.upperEntity = upperEntity;
		this.lowerEntity = lowerEntity;
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
		return ExportAtr.valueOf(this.upperEntity.getAttribute().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getColumnIndex()
	 */
	@Override
	public int getColumnIndex() {
		return (int) this.upperEntity.getId().getColumnIndex();
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
		return this.upperEntity.getUseAtr().compareTo(BigDecimal.ONE) == 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getUpperPosition()
	 */
	@Override
	public Optional<AttendanceRecordDisplay> getUpperPosition() {

		// 13 <= Attribute <=15
		if (this.upperEntity.getAttribute().compareTo(new BigDecimal(12)) == 1
				&& this.upperEntity.getAttribute().compareTo(new BigDecimal(16)) == -1)
			return Optional.of(new SingleAttendanceRecord((SingleAttendanceRecordGetMemento) upperEntity));
		// 16<= Attribute <= 18
		if (this.upperEntity.getAttribute().compareTo(new BigDecimal(15)) == 1
				&& this.upperEntity.getAttribute().compareTo(new BigDecimal(16)) == 19)
			return Optional.of(new CalculateAttendanceRecord((CalculateAttendanceRecordGetMemento) upperEntity));

		return Optional.empty();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.
	 * AttendanceRecordExportGetMemento#getLowerPosition()
	 */
	@Override
	public Optional<AttendanceRecordDisplay> getLowerPosition() {

		// 13 <= Attribute <=15
		if (this.lowerEntity.getAttribute().compareTo(new BigDecimal(12)) == 1
				&& this.lowerEntity.getAttribute().compareTo(new BigDecimal(16)) == -1)
			return Optional.of(new SingleAttendanceRecord((SingleAttendanceRecordGetMemento) lowerEntity));
		// 16<= Attribute <= 18
		if (this.lowerEntity.getAttribute().compareTo(new BigDecimal(15)) == 1
				&& this.lowerEntity.getAttribute().compareTo(new BigDecimal(16)) == 19)
			return Optional.of(new CalculateAttendanceRecord((CalculateAttendanceRecordGetMemento) lowerEntity));

		return Optional.empty();
	}

}
