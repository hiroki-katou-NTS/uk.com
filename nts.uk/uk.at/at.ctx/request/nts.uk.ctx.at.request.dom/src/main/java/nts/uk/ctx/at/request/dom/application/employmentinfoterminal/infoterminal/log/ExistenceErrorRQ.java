package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;
/**
 * エラーの有無
 * @author ThanhNX
 *
 */
public enum ExistenceErrorRQ {
	/** エラーなし */
	NO_ERROR(0),
	/** エラーあり */
	HAVE_ERROR(1);
	public final int value;
	private ExistenceErrorRQ(int type){
		this.value = type;
	}
}
