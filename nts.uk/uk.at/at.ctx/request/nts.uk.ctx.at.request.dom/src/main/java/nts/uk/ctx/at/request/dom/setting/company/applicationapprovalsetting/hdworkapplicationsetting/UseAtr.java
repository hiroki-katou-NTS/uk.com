package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min =0)
public enum UseAtr {
	// しない
	// 定時もしくは実績を表示
	NOT_USE(0),
	//  する
	// 空白
	USE(1);
	public final int value;
}
