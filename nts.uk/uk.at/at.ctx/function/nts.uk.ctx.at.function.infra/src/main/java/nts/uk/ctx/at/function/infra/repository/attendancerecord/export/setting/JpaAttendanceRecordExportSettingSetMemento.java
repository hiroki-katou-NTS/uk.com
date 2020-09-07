package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportFontSize;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.MonthlyConfirmedDisplay;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutseal;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSet;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSetPK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class JpaAttendanceRecordExportSettingSetMemento.
 */
@Getter
public class JpaAttendanceRecordExportSettingSetMemento implements AttendanceRecordExportSettingSetMemento {
	
	private KfnmtRptWkAtdOut resEntity;
	
	private List<KfnmtRptWkAtdOutseal> sealEntity = new ArrayList<>();
 	
	private List<KfnmtRptWkAtdOutframe> outFrameEntity = new ArrayList<>();
	/**
	 * Instantiates a new jpa attendance record export setting set memento.
	 */
	public JpaAttendanceRecordExportSettingSetMemento() {

	}

	/**
	 * Instantiates a new jpa attendance record export setting set memento.
	 *
	 * @param entity
	 *            the entity
	 * @param sealColumnNames
	 *            the seal column entity
	 */
	public JpaAttendanceRecordExportSettingSetMemento(KfnmtRptWkAtdOut entity) {
		this.resEntity = entity;
		if(this.resEntity.getLayoutID() == null){
			this.resEntity.setLayoutID("");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.resEntity.setCid(companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setDailyExportItem(java.util.
	 * List)
	 */
	@Override
	public void setDailyExportItem(List<AttendanceRecordExport> attendanceList) {
		// No Code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setMonthlyExportItem(java.util.
	 * List)
	 */
	@Override
	public void setMonthlyExportItem(List<AttendanceRecordExport> attendanceList) {
		// No Code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setSealUseAtr(java.lang.Boolean)
	 */
	@Override
	public void setSealUseAtr(Boolean atr) {
        if (atr != null)
            this.resEntity.setSealUseAtr(atr ? BigDecimal.ONE : BigDecimal.ZERO);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setCode(nts.uk.ctx.at.function.
	 * dom.attendancerecord.export.setting.ExportSettingCode)
	 */
	@Override
	public void setCode(ExportSettingCode code) {
		this.resEntity.setExportCD(code.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setName(nts.uk.ctx.at.function.
	 * dom.attendancerecord.export.setting.ExportSettingName)
	 */
	@Override
	public void setName(ExportSettingName name) {
		this.resEntity.setName(name.v());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingSetMemento#setSealStamp(java.lang.String,
	 * nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * ExportSettingCode, java.util.List)
	 */
	@Override
	public void setSealStamp(List<SealColumnName> seal) {

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingSetMemento#setNameUseAtr(java.lang.Integer)
	 */
	@Override
	public void setNameUseAtr(Integer nameUseAtr) {
		this.resEntity.setNameUseAtr(new BigDecimal(nameUseAtr));
	}

	@Override
	public void setExportFontSize(Integer exportFontSize) {
		this.resEntity.setCharSizeType(new BigDecimal(exportFontSize));
		
	}

	@Override
	public void setMonthlyConfirmedDisplay(Integer monthlyConfirmedDisplay) {
		this.resEntity.setMonthAppDispAtr(new BigDecimal(monthlyConfirmedDisplay));
		
	}
}
