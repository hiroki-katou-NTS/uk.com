package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveSetOutput {

	//・振休管理区分(true, false)
	private boolean subManageFlag;
	//・振休使用期限
	private int expirationOfLeave;
}
