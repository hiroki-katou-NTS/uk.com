package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command;

import lombok.Data;

@Data
public class MenuOperationCmd {
	// プログラムID
	private String programId;
	// メニューを使用する
	private int useMenu;
	//承認機能を使用する
	private int useApproval;
	// 通知機能を使用する
	private int useNotice;
}
