package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PrintSettingRemarksColumn {

	//印字しない
	NOT_PRINT_REMARK(0),
	
	//印字する
	PRINT_REMARK(1);
	
	public final int value;
}
