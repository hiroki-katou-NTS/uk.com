package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutseal;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstSealColumn;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnstAttndRecOutSet;

/**
 * The Class JpaAttendanceRecordExportSettingGetMemento.
 */
public class JpaAttendanceRecordExportSettingGetMemento implements AttendanceRecordExportSettingGetMemento {

//	/** The attendance entity. */
//	private KfnstAttndRecOutSet attendanceEntity;
//
//	/** The seal column entity. */
//	private List<KfnstSealColumn> sealColumnEntity;
	
	/** The atd entity. */
	private KfnmtRptWkAtdOut atdEntity;
	
	/** The seal entity. */
	private List<KfnmtRptWkAtdOutseal> sealEntity;

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
//	public JpaAttendanceRecordExportSettingGetMemento(KfnstAttndRecOutSet attendanceEntity,
//			List<KfnstSealColumn> sealColumnEntity) {
//		this.attendanceEntity = attendanceEntity;
//		this.sealColumnEntity = sealColumnEntity;
//	}
	
	
	// TODO UPDATE DUCNT
	/**
	 * Instantiates a new jpa attendance record export setting get memento.
	 *
	 * @param atdEntity the atd entity
	 * @param sealEntity the seal entity
	 */
	public JpaAttendanceRecordExportSettingGetMemento(KfnmtRptWkAtdOut atdEntity,
			List<KfnmtRptWkAtdOutseal> sealEntity) {
		super();
		this.atdEntity = atdEntity;
		this.sealEntity = sealEntity;
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

	// TODO UPDATE DUCNT
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getSealUseAtr()
	 */
	@Override
	public Boolean getSealUseAtr() {
		return this.atdEntity.getSealUseAtr().compareTo(BigDecimal.ONE) == 0 ? true : false;
//		return this.attendanceEntity.getSealUseAtr().compareTo(BigDecimal.ONE) == 0 ? true : false;
	}
	
	

	// TODO UPDATE DUCNT
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getCode()
	 */
	@Override
	public ExportSettingCode getCode() {
		return new ExportSettingCode(this.atdEntity.getExportCD());
//		return new ExportSettingCode(String.valueOf(this.attendanceEntity.getId().getExportCd()));
	}

	// TODO UPDATE DUCNT
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getName()
	 */
	@Override
	public ExportSettingName getName() {
		return new ExportSettingName(this.atdEntity.getName());
//		return new ExportSettingName(this.attendanceEntity.getName());
	}

	// TODO UPDATE DUCNT
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingGetMemento#getSealStamp()
	 */
	@Override
	public List<SealColumnName> getSealStamp() {
		List<SealColumnName> list = new ArrayList<>();

		this.sealEntity.forEach(item -> {
			list.add(new SealColumnName(item.getSealStampName()));
		});
//		this.sealColumnEntity.forEach(item -> {
//			list.add(new SealColumnName(item.getSealStampName()));
//		});
		return list;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento#getNameUseAtr()
	 */
	@Override
	public Integer getNameUseAtr() {
		return this.atdEntity.getNameUseAtr().intValue();
//		return this.attendanceEntity.getNameUseAtr().intValue();
	}

	@Override
	public Integer getExportFontSize() {
		return this.atdEntity.getCharSizeType().intValue();
//		return this.attendanceEntity.getCharSizeType().intValue();
	}

	@Override
	public Integer getMonthlyConfirmedDisplay() {
		return this.atdEntity.getMonthAppDispAtr().intValue();
//		return this.attendanceEntity.getMonthAppDispAtr().intValue();
	}
}
