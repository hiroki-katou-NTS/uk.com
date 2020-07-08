package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;
/**
 * エラーの有無
 * @author chungnt
 *
 */
public enum ExistenceError {
	/** エラーなし */
	NO_ERROR(0),
	/** エラーあり */
	HAVE_ERROR(1);
	public final int value;
	private ExistenceError(int type){
		this.value = type;
	}
}
