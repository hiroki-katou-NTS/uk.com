package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 3, min =0)
public enum WorkChangeSet {
	// 変更しない
	NOT_CHANGE(0), 
	// 変更する
	CHANGE(1), 
	// 申請時に決める（初期選択：勤務を変更しない）
	DECIDED_NOT_CHANGE(2),
	// 申請時に決める（初期選択：勤務を変更する）
	DECIDE_CHANGE(3);
	public final int value;
}
