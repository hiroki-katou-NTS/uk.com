package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * ジャスト補正区分
 * @author ken_takasu
 *
 */
public enum JustCorrectionAtr {
	/** The not use. */
	NOT_USE,

	/** The use. */
	USE;


	/**
	 * するか判定する
	 * @return　使用するである
	 */
	public boolean isUse() {
		return USE.equals(this);
	}
	
	/**
	 * しないか判定する
	 * @return　使用しないである
	 */
	public boolean isNotUse() {
		return NOT_USE.equals(this);
	}
}
