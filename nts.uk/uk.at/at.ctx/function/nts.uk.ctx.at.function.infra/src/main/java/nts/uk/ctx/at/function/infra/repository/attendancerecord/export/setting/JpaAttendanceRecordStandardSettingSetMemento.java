package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

@Data
public class JpaAttendanceRecordStandardSettingSetMemento implements AttendanceRecordStandardSetting.MementoSetter {
	
	/** The kfnmt rpt wk atd out. */
	private List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOut;
	
	/** The compnay id. */
	private String cid;
	
	/** The selection. */
	private int itemSelectionType;

	@Override
	public void setAttendanceRecordExportSettings(List<AttendanceRecordExportSetting> standardItem) {
		this.kfnmtRptWkAtdOut = standardItem.stream().map(i -> {
			KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
			i.saveToMemento(entity);
			return entity;
		}).collect(Collectors.toList());
	}
}
