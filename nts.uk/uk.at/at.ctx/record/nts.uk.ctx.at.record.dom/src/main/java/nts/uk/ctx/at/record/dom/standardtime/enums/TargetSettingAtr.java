package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum TargetSettingAtr {
	
	/*
	 * 対象区分
	 */
	// 0: 月別実績データ
	MONTHLY_ACTUAL_DATA(0),
	// 1: 補助月データ
	AUXILIARY_MONTH_DATA(1);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "月別実績データ";
			break;
		case 1:
			name = "補助月データ";
			break;
		default:
			name = "月別実績データ";
			break;
		}
		return name;
	}
}
