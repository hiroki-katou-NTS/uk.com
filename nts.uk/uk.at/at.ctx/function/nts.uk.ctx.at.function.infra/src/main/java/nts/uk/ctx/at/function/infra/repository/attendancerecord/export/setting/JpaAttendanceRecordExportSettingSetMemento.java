package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSet;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSetPK;

/**
 * The Class JpaAttendanceRecordExportSettingSetMemento.
 */
public class JpaAttendanceRecordExportSettingSetMemento implements AttendanceRecordExportSettingSetMemento {

	/** The entity. */
	private KfnstAttndRecOutSet entity;

	/** The seal column entity. */
	private List<KfnstSealColumn> sealColumnEntity;

	private List<SealColumnName> sealColumnNames;

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
	public JpaAttendanceRecordExportSettingSetMemento(KfnstAttndRecOutSet entity,
			List<SealColumnName> sealColumnNames) {
		this.entity = entity;
		this.sealColumnNames = sealColumnNames;
		if(this.entity.getId()==null){
			this.entity.setId(new KfnstAttndRecOutSetPK());
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
		this.entity.getId().setCid(companyId);

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
		this.entity.setSealUseAtr(atr ? BigDecimal.ONE : BigDecimal.ZERO);

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
		this.entity.getId().setExportCd(code.v());

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
		this.entity.setName(name.toString());

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
	public void setSealStamp(String companyId, ExportSettingCode code, List<SealColumnName> seal) {
		seal.forEach(item -> {
			UID columnId = new UID();
			KfnstSealColumn tempEntity = new KfnstSealColumn(columnId.toString(), companyId, new BigDecimal(code.v()), item.toString());			
			this.sealColumnEntity.add(tempEntity);
		});

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingSetMemento#setNameUseAtr(java.lang.Integer)
	 */
	@Override
	public void setNameUseAtr(Integer nameUseAtr) {
		this.entity.setNameUseAtr(new BigDecimal(nameUseAtr));
	}

}
