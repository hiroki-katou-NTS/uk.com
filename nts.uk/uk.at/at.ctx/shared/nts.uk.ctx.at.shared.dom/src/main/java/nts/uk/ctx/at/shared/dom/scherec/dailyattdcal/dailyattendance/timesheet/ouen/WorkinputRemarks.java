package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 作業入力備考
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別時間帯別勤怠.応援実績.時間帯.作業入力備考
 * @author laitv
 *
 */
@StringMaxLength(1000)
public class WorkinputRemarks extends StringPrimitiveValue<WorkinputRemarks>{

	private static final long serialVersionUID = -6424811880380547242L;

	public WorkinputRemarks(String rawValue) {
		super(rawValue);
	}

}
