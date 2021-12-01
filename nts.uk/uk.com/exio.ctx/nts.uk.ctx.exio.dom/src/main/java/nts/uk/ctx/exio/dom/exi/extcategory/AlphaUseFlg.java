package nts.uk.ctx.exio.dom.exi.extcategory;

import lombok.AllArgsConstructor;

/**
 * アルファベット禁止区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum AlphaUseFlg {
	/**	アルファベット許可 */
	YES_ALPHA(0),
	/**	小文字アルファベット禁止 */
	NO_LOWERCASE(1),
	/**	大文字アルファベット禁止*/
	NO_UPPERCASE(2),
	/**
	 *アルファベット禁止（小文字・大文字ともに） 
	 */
	NO_ALPHA(3);
	
	public final Integer value;
}
