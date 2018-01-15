package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;
/**
 * 直行直帰区分
 * @author ducpm
 *
 */
public enum GoBackDirectAtr {

	/**
	 * 0:ない
	 */
	NOT(0),
	/**
	 * 1: ある
	 */
	IS(1);

	public int value;

	GoBackDirectAtr(int type){
		this.value = type;
	}
}
