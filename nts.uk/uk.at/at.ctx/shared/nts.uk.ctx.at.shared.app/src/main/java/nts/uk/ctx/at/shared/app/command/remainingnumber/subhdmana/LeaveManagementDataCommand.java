package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class LeaveManagementDataCommand {
	// ID
	private String leaveId;

	// 社員ID
	private String employeeId;

	// 休出日
	private GeneralDate leaveDate;

	// 使用期限日
	private GeneralDate expiredDate;

	// 発生日数
	private Double occurredDays;

	// 未使用日数
	private Double unUsedDays;

	// 期限切れ
	private Boolean isCheckedExpired;

	// 締めID
	private Integer closureId;
	
	private int unknownDate;
}
