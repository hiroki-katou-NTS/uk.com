package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.出勤簿.出勤簿の出力項目自由設定
 * 
 * @author nws-ducnt
 *
 */
@Getter
public class AttendanceRecordFreeSetting extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private CompanyId cid;

	/**
	 * 出力項目: 出勤簿の出力項目設定 (List)
	 */
	private List<AttendanceRecordExportSetting> attendanceRecordExportSettings;

	/**
	 * 社員ID
	 */
	private EmployeeId employeeId;

	/**
	 * 項目選択種類
	 */
	private ItemSelectionType itemSelectionType;

	/**
	 * Instantiates a new attendance record ouput items.
	 */
	private AttendanceRecordFreeSetting() {}

	/**
	 * Creates the from memento.
	 *
	 * @param memento the memento
	 * @return the attendance record ouput items
	 */
	public static AttendanceRecordFreeSetting createFromMemento(MementoGetter memento) {
		AttendanceRecordFreeSetting domain = new AttendanceRecordFreeSetting();
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
		if (memento.getCid() == null) {
			this.cid = new CompanyId(AppContexts.user().companyId());
		} else {
			this.cid = new CompanyId(memento.getCid());
		}
		this.attendanceRecordExportSettings = memento.getAttendanceRecordExportSettings();
		this.employeeId = new EmployeeId(memento.getEmployeeId());
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
		memento.setEmployeeId(this.employeeId.v());
		if (this.itemSelectionType != null) {
			memento.setItemSelectionType(this.itemSelectionType.value);
		}
	}

	/**
	 * The Interface MementoSetter.
	 *
	 * 
	 */
	public static interface MementoSetter {
		void setCid(String cid);

		void setAttendanceRecordExportSettings(List<AttendanceRecordExportSetting> attendanceRecordExportSettings);

		void setEmployeeId(String employeeId);

		void setItemSelectionType(int itemSelectionType);
	}

	/**
	 * The Interface MementoGetter.
	 */
	public static interface MementoGetter {
		String getCid();

		List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings();

		String getEmployeeId();

		int getItemSelectionType();
	}

}
