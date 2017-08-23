package nts.uk.ctx.at.schedule.dom.shift.total.times.setting;
/**
 *  
 * @author TanNH
 *
 */
public enum UseAtr {
	
	/** The Not use. */
	NotUse(0),
	
	/** The Use. */
	Use(1);
	
	/** The value. */
	public final int value;
	
	/**
	 * Instantiates a new use atr.
	 *
	 * @param value the value
	 */
	UseAtr(int value){
		this.value = value;
	}
}
