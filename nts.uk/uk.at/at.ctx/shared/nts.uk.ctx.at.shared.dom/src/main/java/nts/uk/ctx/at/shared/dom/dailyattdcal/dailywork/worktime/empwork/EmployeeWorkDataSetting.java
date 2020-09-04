package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;

/**
 * @author thanh_nx
 *
 *         社員の作業データ設定
 */
@Getter
public class EmployeeWorkDataSetting implements DomainAggregate {

	// 社員ID
	private final String employeeId;

	// 勤務職場: 職場ID
	private final String workplaceId;

	// 勤務場所: 場所コード
	private final WorkLocationCD workLocationCD;

	// 作業グループ: 作業グループ
	private final Optional<WorkGroup> workGroup;

	public EmployeeWorkDataSetting(String employeeId, String workplaceId, WorkLocationCD workLocationCD,
			Optional<WorkGroup> workGroup) {
		super();
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
		this.workLocationCD = workLocationCD;
		this.workGroup = workGroup;
	}

}
