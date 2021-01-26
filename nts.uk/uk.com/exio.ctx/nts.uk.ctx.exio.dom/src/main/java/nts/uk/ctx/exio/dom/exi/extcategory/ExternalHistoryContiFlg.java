package nts.uk.ctx.exio.dom.exi.extcategory;

import lombok.AllArgsConstructor;

/**
 * 履歴継続区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ExternalHistoryContiFlg {
	/**	履歴を継続しなくてもよい */
	NO_HIS_CONTINUE(0),
	/**	履歴を継続しなくてはいけない */
	HIS_CONTINUE(1);
	
	public final Integer value;
}
