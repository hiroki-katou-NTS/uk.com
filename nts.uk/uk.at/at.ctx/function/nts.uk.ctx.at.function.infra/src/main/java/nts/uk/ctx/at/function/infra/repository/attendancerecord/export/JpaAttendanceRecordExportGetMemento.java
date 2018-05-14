package nts.uk.ctx.at.function.infra.repository.attendancerecord.export;

import java.math.BigDecimal;
import java.util.Optional;

import org.eclipse.persistence.jpa.config.Convert;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;
import nts.uk.ctx.at.function.dom.attendancerecord.item.AttendanceRecordDisplay;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;

public class JpaAttendanceRecordExportGetMemento implements AttendanceRecordExportGetMemento {

	private KfnstAttndRec entity;
	
	public JpaAttendanceRecordExportGetMemento(KfnstAttndRec entity) {
		this.entity = entity;
	}
	
	JpaAttendanceRecordExportGetMemento(){
		
	}
	
	@Override
	public ExportAtr getExportAtr() {
		return ExportAtr.valueOf(this.entity.getAttribute().intValue());
	}

	@Override
	public int getColumnIndex() {
		return (int) this.entity.getId().getColumnIndex();
	}

	@Override
	public Boolean isUseAtr() {
		return null;
	}

	@Override
	public Optional<AttendanceRecordDisplay> getUpperPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AttendanceRecordDisplay> getLowerPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
