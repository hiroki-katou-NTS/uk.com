package nts.uk.ctx.at.function.app.command.attendancerecord.export.setting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AttendanceRecordExportSettingCommand.
 */
@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExportSettingCommand {

	/** The seal use atr. */
	//	印鑑欄使用区分
	Boolean sealUseAtr;
	
	/** The code. */
	//	コード	
	String code;

	/** The name. */
	//	名称
	String name;

	/** The seal stamp. */
	//	印鑑欄
	List<String> sealStamp;
	
	/** The name use atr. */
	//	名称使用区分
	Integer nameUseAtr;
	
	//	文字の大きさ
	String exportFontSize;
	
	/** The once update. */
	boolean onceUpdate;
	
	/** The monthly display. */
	Integer monthlyDisplay;
	
	/** The item sel type. */
	Integer itemSelType;
	/**
	 * Instantiates a new attendance record export setting add command.
	 */
	public AttendanceRecordExportSettingCommand() {
		super();
	}
}
