package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuInfoEx {
	// プログラムID
	private String programId;
	// プログラム名
	private String programName;
	//承認機能を使用する
	private int useApproval;
}
