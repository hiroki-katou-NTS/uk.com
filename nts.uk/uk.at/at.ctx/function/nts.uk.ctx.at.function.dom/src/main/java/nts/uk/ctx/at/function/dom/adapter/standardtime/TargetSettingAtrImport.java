package nts.uk.ctx.at.function.dom.adapter.standardtime;

/**
 * @author dat.lh
 *
 */
public enum TargetSettingAtrImport {
	/*
	 * 対象区分
	 */
	// 0: 月別実績データ
	MONTHLY_ACTUAL_DATA(0),
	// 1: 補助月データ
	AUXILIARY_MONTH_DATA(1);

	public final int value;
	
	private TargetSettingAtrImport(int type) {
		this.value = type;
	}
}
