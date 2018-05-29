package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSet;

/**
 * The Class JpaAttendanceRecordExportSettingGetMemento.
 */
public class JpaAttendanceRecordExportSettingGetMemento implements AttendanceRecordExportSettingGetMemento {

	/** The attendance entity. */
	private KfnstAttndRecOutSet attendanceEntity;

	/** The seal column entity. */
	private List<KfnstSealColumn> sealColumnEntity;

	/**
	 * Instantiates a new jpa attendance record export setting get memento.
	 */
	public JpaAttendanceRecordExportSettingGetMemento() {

	}

	/**
	 * Instantiates a new jpa attendance record export setting get memento.
	 *
	 * @param attendanceEntity
	 *            the attendance entity
	 * @param sealColumnEntity
	 *            the seal column entity
	 */
	public JpaAttendanceRecordExportSettingGetMemento(KfnstAttndRecOutSet attendanceEntity,
			List<KfnstSealColumn> sealColumnEntity) {
		this.attendanceEntity = attendanceEntity;
		this.sealColumnEntity = sealColumnEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		// No Code
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getDailyExportItem()
	 */
	@Override
	public List<AttendanceRecordExport> getDailyExportItem() {
		// No Code
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getMonthlyExportItem()
	 */
	@Override
	public List<AttendanceRecordExport> getMonthlyExportItem() {
		// No Code
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getSealUseAtr()
	 */
	@Override
	public Boolean getSealUseAtr() {
		return this.attendanceEntity.getSealUseAtr().compareTo(BigDecimal.ONE) == 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getCode()
	 */
	@Override
	public ExportSettingCode getCode() {
		return new ExportSettingCode(this.attendanceEntity.getId().getExportCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getName()
	 */
	@Override
	public ExportSettingName getName() {
		return new ExportSettingName(this.attendanceEntity.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getSealStamp()
	 */
	@Override
	public List<SealColumnName> getSealStamp() {
		List<SealColumnName> list = new ArrayList<>();

		this.sealColumnEntity.forEach(item -> {
			list.add(new SealColumnName(item.getSealStampName()));
		});
		return list;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento#getNameUseAtr()
	 */
	@Override
	public Integer getNameUseAtr() {
		return this.attendanceEntity.getNameUseAtr().intValue();
	}
}
