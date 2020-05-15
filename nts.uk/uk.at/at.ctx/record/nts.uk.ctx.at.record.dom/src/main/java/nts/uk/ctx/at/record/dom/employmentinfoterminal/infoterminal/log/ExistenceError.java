package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;
/**
 * エラーの有無
 * @author ThanhNX
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
