package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum WorkChange {
	// 申請時に決める（初期選択：勤務を変更する）
	DECIDE_APPLI(0),
	//  申請時に決める（初期選択：勤務を変更しない）
	DECIDE_TIME_APPLI(1),
	// 変更する
	CHANGE(2),
	// 変更しない
	NOT_CHANGE(3);
	public final int value;
}
