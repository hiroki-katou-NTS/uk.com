package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;

@Getter
@AllArgsConstructor
public class LeaveSetOutput {

	//・振休管理区分(true, false)
	private boolean subManageFlag;
	//・振休使用期限
	private int expirationOfLeave;
	
	//add refactor RequestList203
	//振休先取り許可
	private ApplyPermission applyPermission;
}
