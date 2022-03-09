package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StampClassifi;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;

import java.util.Optional;

/**
 * @author ThanhNX
 *
 *         出退区分NR
 */
public enum LeaveCategory {

	WORK("A", "出勤"),

	WORK_HALF("B", "出勤＋半休"),

	WORK_FLEX("D", "出勤＋ﾌﾚｯｸｽ"),

	LEAVE("G", "退勤"),

	LEAVE_HALF("H", "退勤＋半休"),

	LEAVE_OVERTIME("J", "退勤＋残業"),

	LEAVE_FLEX("L", "退勤＋ﾌﾚｯｸｽ"),

	GO_OUT("O", "外出"),

	RETURN("Q", "戻り"),

	EARLY("S", "早出"),

	VACATION("U", "休出"),

	WORK_TEMPORARY("0", "出勤＋臨時"),

	RETURN_START("1", "（入）から戻る／開始"),

	GO_EN("2", "（出）に行く／終了"),

	WORK_ENTRANCE("3", "出勤＋入"),

	WORK_HALF_ENTRANCE("4", "出勤＋半休＋入"),

	WORK_FLEX_ENTRANCE("5", "出勤＋ﾌﾚｯｸｽ＋入"),

	VACATION_ENTRANCE("6", "休出＋入"),

	TEMPORARY_ENTRANCE("7", "臨時＋入"),

	EARLY_ENTRANCE("8", "早出＋入"),

	RETIRED_TEMPORARY("9", "退勤＋臨時");

	public final String value;

	public final String nameType;

	private static final LeaveCategory[] values = LeaveCategory.values();

	private LeaveCategory(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static LeaveCategory valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (LeaveCategory val : LeaveCategory.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}

	// [1] 出退区分NRから打刻分類を変換する
	public Optional<StampClassifi> convertStampClassifi() {
		switch (this) {
		case WORK:
		case WORK_HALF:
		case WORK_FLEX:
		case EARLY:
		case VACATION:
			return Optional.of(StampClassifi.ATTENDANCE);
		case LEAVE:
		case LEAVE_HALF:
		case LEAVE_OVERTIME:
		case LEAVE_FLEX:
			return Optional.of(StampClassifi.LEAVING);
		case GO_OUT:
			return Optional.of(StampClassifi.GO_OUT);
		case RETURN:
			return Optional.of(StampClassifi.GO_BACK);
		default:
			return Optional.empty();
		}

	}

	public Optional<ChangeClockAtr> toChangeClockAtr(boolean changesToEntryExit) {

		switch (this) {
			case WORK:
			case WORK_HALF:
			case WORK_FLEX:
				if (changesToEntryExit)
					return Optional.of(ChangeClockAtr.OVER_TIME);
				return Optional.of(ChangeClockAtr.GOING_TO_WORK);

			case EARLY:
			case VACATION:
				return Optional.of(ChangeClockAtr.GOING_TO_WORK);

			case LEAVE:
			case LEAVE_HALF:
			case LEAVE_OVERTIME:
			case LEAVE_FLEX:
				if (changesToEntryExit)
					return Optional.of(ChangeClockAtr.BRARK);
				return Optional.of(ChangeClockAtr.WORKING_OUT);

			case GO_OUT:
				if (changesToEntryExit)
					return Optional.of(ChangeClockAtr.START_OF_SUPPORT);
				return Optional.of(ChangeClockAtr.GO_OUT);

			case RETURN:
				if (changesToEntryExit)
					return Optional.of(ChangeClockAtr.END_OF_SUPPORT);
				return Optional.of(ChangeClockAtr.RETURN);

			case WORK_TEMPORARY:
				return Optional.of(ChangeClockAtr.TEMPORARY_WORK);

			case RETURN_START:
				return Optional.of(ChangeClockAtr.START_OF_SUPPORT);

			case GO_EN:
				return Optional.of(ChangeClockAtr.END_OF_SUPPORT);

			case WORK_ENTRANCE:
			case WORK_HALF_ENTRANCE:
			case WORK_FLEX_ENTRANCE:
				return Optional.of(ChangeClockAtr.GOING_TO_WORK);

			case VACATION_ENTRANCE:
			case EARLY_ENTRANCE:
				return Optional.of(ChangeClockAtr.START_OF_SUPPORT);

			case TEMPORARY_ENTRANCE:
				return Optional.of(ChangeClockAtr.TEMPORARY_WORK);

			case RETIRED_TEMPORARY:
				return Optional.of(ChangeClockAtr.TEMPORARY_LEAVING);

			default:
				return Optional.empty();
		}
	}

	/**
	 * 計算区分変更対象
	 */
	public ChangeCalArt toChangeCalArt() {
		switch (this) {

			case WORK_FLEX:
			case LEAVE_FLEX:
			case WORK_FLEX_ENTRANCE:
				return ChangeCalArt.FIX;

			case LEAVE_OVERTIME:
				return ChangeCalArt.OVER_TIME;

			case EARLY:
			case EARLY_ENTRANCE:
				return ChangeCalArt.EARLY_APPEARANCE;

			case VACATION:
			case VACATION_ENTRANCE:
				return ChangeCalArt.BRARK;

			default:
				return ChangeCalArt.NONE;
		}
	}

	public boolean isHalfDay() {
		return this == LeaveCategory.WORK_HALF
				|| this == LeaveCategory.LEAVE_HALF
				|| this == LeaveCategory.WORK_HALF_ENTRANCE;
	}
}
