package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 */
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotReferringAchAtr {

	/** 休日時の就業時間帯*/
	WORKING_HOURS_DURING_HOLIDAYS(0),
	/** 平日時の就業時間帯*/
	WORKING_HOURS_DURING_WEEKDAY(1);

	public final int value;
}
