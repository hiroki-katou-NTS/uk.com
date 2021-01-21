package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums;

/**
 * 
 * @author nampt
 *
 */
public enum TargetSettingAtr {
	
	/*
	 * 対象区分
	 */
	// 0: 月別実績データ
	MONTHLY_ACTUAL_DATA(0),
	// 1: 補助月データ
	AUXILIARY_MONTH_DATA(1);

	public final int value;
	
	private TargetSettingAtr(int type) {
		this.value = type;
	}
}
