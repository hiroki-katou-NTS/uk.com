package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;


/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.出勤簿.出勤簿の出力項目定型設定
 * 
 * @author nws-ducnt
 *
 */
@Getter
public class AttendanceRecordStandardSetting extends AggregateRoot{

	/**
	 * 会社ID
	 */
	private CompanyId cid;
	
	/**
	 * 出力項目: 出勤簿の出力項目設定 (List)
	 */
	private List<AttendanceRecordExportSetting> attendanceRecordExportSettings;
	
	/**
	 * 項目選択種類
	 */
	private ItemSelectionType itemSelectionType;
	
	
	/**
	 * Instantiates a new attendance record standard setting.
	 */
	public AttendanceRecordStandardSetting() {}

	/**
	 * Creates the from memento.
	 *
	 * @param memento the memento
	 * @return the attendance record standard setting
	 */
	public static AttendanceRecordStandardSetting createFromMemento(MementoGetter memento) {
		AttendanceRecordStandardSetting domain = new AttendanceRecordStandardSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	/**
	 * Gets the memento.
	 *
	 * @param memento the memento
	 * @return the memento
	 */
	public void getMemento(MementoGetter memento) {
		this.cid = new CompanyId(memento.getCid());
		this.attendanceRecordExportSettings = memento.getAttendanceRecordExportSettings();
		this.itemSelectionType = ItemSelectionType.valueOf(memento.getItemSelectionType());
	}

	
	/**
	 * Sets the memento.
	 *
	 * @param memento the new memento
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCid(this.cid.v());
		if (this.attendanceRecordExportSettings != null) {
			memento.setAttendanceRecordExportSettings(this.attendanceRecordExportSettings);
		}
		if (this.itemSelectionType !=null ) {
			memento.setItemSelectionType(this.itemSelectionType.value);
		}
	}

	
	/**
	 * The Interface MementoSetter.
	 */
	public static interface MementoSetter {
		void setCid(String cid);
		
		void setAttendanceRecordExportSettings(List<AttendanceRecordExportSetting> attendanceRecordExportSettings);
		
		void setItemSelectionType(int itemSelectionType);
	}

	
	/**
	 * The Interface MementoGetter.
	 */
	public static interface MementoGetter {
		String getCid();
		
		List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings();
		
		int getItemSelectionType();
	}
	
}
