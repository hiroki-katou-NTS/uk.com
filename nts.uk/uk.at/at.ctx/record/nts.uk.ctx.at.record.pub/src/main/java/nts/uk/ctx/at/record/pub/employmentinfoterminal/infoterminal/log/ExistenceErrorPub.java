package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;
/**
 * エラーの有無
 * @author ThanhNX
 *
 */
public enum ExistenceErrorPub {
	/** エラーなし */
	NO_ERROR(0),
	/** エラーあり */
	HAVE_ERROR(1);
	public final int value;
	private ExistenceErrorPub(int type){
		this.value = type;
	}
}
