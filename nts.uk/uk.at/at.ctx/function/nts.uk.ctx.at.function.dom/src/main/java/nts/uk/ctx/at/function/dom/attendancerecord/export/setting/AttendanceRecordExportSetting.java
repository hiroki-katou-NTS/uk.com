package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AttendanceRecordOutputSetting.
 */
// 出勤簿の出力項目設定

@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordExportSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The daily ouput item. */
	// 日次の出力項目
	private List<AttendanceRecordExport> dailyExportItem;

	/** The monthly output item. */
	// 月次の出力項目
	private List<AttendanceRecordExport> monthlyExportItem;

	/** The seal use atr. */
	// 印鑑欄使用区分
	private Boolean sealUseAtr;

	/** The code. */
	// コード
	private ExportSettingCode code;

	/** The name. */
	// 名称
	private ExportSettingName name;

	/** The seal stamp. */
	// 印鑑欄
	private List<SealColumnName> sealStamp;

	/** The name use atr. */
	//名称使用区分
	private NameUseAtr nameUseAtr;

	/**
	 * Instantiates a new attendance record export setting.
	 */
	public AttendanceRecordExportSetting() {
		super();
	}

	/**
	 * Instantiates a new attendance record export setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public AttendanceRecordExportSetting(AttendanceRecordExportSettingGetMemento memento) {
		this.companyId = AppContexts.user().companyId();
		this.dailyExportItem = memento.getDailyExportItem();
		this.monthlyExportItem = memento.getMonthlyExportItem();
		this.sealUseAtr = memento.getSealUseAtr();
		this.code = memento.getCode();
		this.name = memento.getName();
		this.sealStamp = memento.getSealStamp();
		this.nameUseAtr =  NameUseAtr.valueOf(memento.getNameUseAtr());

	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AttendanceRecordExportSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setCode(this.code);
//		memento.setDailyExportItem(this.dailyExportItem);
//		memento.setMonthlyExportItem(this.monthlyExportItem);
		memento.setName(this.name);
//		memento.setSealStamp(this.sealStamp);
		memento.setSealUseAtr(this.sealUseAtr);
		if (this.nameUseAtr != null)
			memento.setNameUseAtr(this.nameUseAtr.value);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((dailyExportItem == null) ? 0 : dailyExportItem.hashCode());
		result = prime * result + ((monthlyExportItem == null) ? 0 : monthlyExportItem.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sealStamp == null) ? 0 : sealStamp.hashCode());
		result = prime * result + ((sealUseAtr == null) ? 0 : sealUseAtr.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj
	 *            the obj
	 * @return true, if successful
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttendanceRecordExportSetting other = (AttendanceRecordExportSetting) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (dailyExportItem == null) {
			if (other.dailyExportItem != null)
				return false;
		} else if (!dailyExportItem.equals(other.dailyExportItem))
			return false;
		if (monthlyExportItem == null) {
			if (other.monthlyExportItem != null)
				return false;
		} else if (!monthlyExportItem.equals(other.monthlyExportItem))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sealStamp == null) {
			if (other.sealStamp != null)
				return false;
		} else if (!sealStamp.equals(other.sealStamp))
			return false;
		if (sealUseAtr == null) {
			if (other.sealUseAtr != null)
				return false;
		} else if (!sealUseAtr.equals(other.sealUseAtr))
			return false;
		return true;
	}

}
