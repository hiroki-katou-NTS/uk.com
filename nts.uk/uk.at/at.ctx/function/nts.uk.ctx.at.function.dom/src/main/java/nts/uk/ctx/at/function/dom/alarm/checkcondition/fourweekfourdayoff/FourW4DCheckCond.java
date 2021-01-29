package nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff;

import nts.arc.i18n.I18NText;

/** 4週4休チェック条件 */
public enum FourW4DCheckCond {

	/** 実績のみで4週4休をチェックする */
	ForActualResultsOnly(0, I18NText.getText("KAL010_217")),

	/** スケジュールと実績で4週4休をチェックする */
	WithScheduleAndActualResults(1, I18NText.getText("KAL010_218"));

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private FourW4DCheckCond(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
