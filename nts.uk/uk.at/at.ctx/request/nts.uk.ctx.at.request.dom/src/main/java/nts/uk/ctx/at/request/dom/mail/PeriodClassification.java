package nts.uk.ctx.at.request.dom.mail;

/**
 * @author hiep.ld
 *
 */
public enum PeriodClassification {
	YEAR (0),
	MONTH (1),
	DAY (2),
	HOUR (3),
	MINUTE (4);
	private int value;
	private PeriodClassification (int rawValue){
		this.value = rawValue;
	}
}
