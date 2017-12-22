package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 */
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotReferringAchAtr {

	/** 平日時の就業時間帯*/
	WORKING_HOURS_DURING_WEEKDAY(0),
	
	/** 休日時の就業時間帯*/
	WORKING_HOURS_DURING_HOLIDAYS(1);


	public final int value;
}
