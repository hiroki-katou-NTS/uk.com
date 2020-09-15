package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

@Data
public class JpaAttendanceRecordFreeSettingSetMemento implements AttendanceRecordFreeSetting.MementoSetter {

	/** The list kfnmt rpt wk atd out. */
	List<KfnmtRptWkAtdOut> listKfnmtRptWkAtdOut;

	/** The cid. */
	private String cid;

	/** The employee id. */
	private String employeeId;

	/** The item selection type. */
	private int itemSelectionType;

	@Override
	public void setAttendanceRecordExportSettings(List<AttendanceRecordExportSetting> outputItem) {
		this.listKfnmtRptWkAtdOut = outputItem.stream().map(i -> {
			KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
			i.saveToMemento(entity);
			return entity;
		}).collect(Collectors.toList());

	}

}
