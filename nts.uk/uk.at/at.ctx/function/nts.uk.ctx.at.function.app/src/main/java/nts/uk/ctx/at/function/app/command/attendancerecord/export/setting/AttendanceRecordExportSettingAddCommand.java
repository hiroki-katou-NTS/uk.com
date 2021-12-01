package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.DayOfWeek;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportFontSize;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.MonthlyConfirmedDisplay;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;

/**
 * The Class AttendanceRecordExportSettingAddCommand.
 */
@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExportSettingAddCommand implements AttendanceRecordExportSettingGetMemento {

	/** The code. */
	String code;

	/** The name. */
	String name;

	/** The seal stamp. */
	List<String> sealStamp;
	
	/** The seal use atr. */
	Boolean sealUseAtr;

	/** The name use atr. */
	Integer nameUseAtr;
	
	/** The once update. */
	boolean onceUpdate;
	
	/** The font size. */
	Integer exportFontSize;
	
	/** The monthly display. */
	Integer monthlyDisplay;
	
	/** The item sel type. */
	Integer itemSelType;
	
	/** The layout id. */
	String layoutId;
	
	Integer startOfWeek;
	
	/**
	 * Instantiates a new attendance record export setting add command.
	 */
	public AttendanceRecordExportSettingAddCommand() {
		super();
	}

	@Override
	public List<AttendanceRecordExport> getDailyExportItem() {
		return null;
	}

	@Override
	public List<AttendanceRecordExport> getMonthlyExportItem() {
		return null;
	}

	@Override
	public ExportSettingCode getCode() {
		return new ExportSettingCode(this.code);
	}

	@Override
	public ExportSettingName getName() {
		return new ExportSettingName(this.name);
	}

	@Override
	public ExportFontSize getExportFontSize() {
		return ExportFontSize.valueOf(this.exportFontSize);
	}

	@Override
	public MonthlyConfirmedDisplay getMonthlyConfirmedDisplay() {
		return MonthlyConfirmedDisplay.valueOf(this.monthlyDisplay);
	}

	@Override
	public DayOfWeek getStartOfWeek() {
		return EnumAdaptor.valueOf(startOfWeek, DayOfWeek.class);
	}
	
	@Override
	public List<SealColumnName> getSealStamp() {
		return this.sealStamp != null
				? this.sealStamp.stream()
					.map(SealColumnName::new)
					.collect(Collectors.toList())
				: new ArrayList<>();
	}
	
}
