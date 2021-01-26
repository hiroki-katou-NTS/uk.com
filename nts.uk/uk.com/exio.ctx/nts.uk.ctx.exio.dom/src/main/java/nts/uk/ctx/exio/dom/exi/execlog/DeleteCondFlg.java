package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;
/**
 * 	削除条件
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DeleteCondFlg {
	/**	全て削除してから受入 */
	ALL_DELETE(0),
	/**	対象データのみ削除してから受入 */
	TAGET_DATA_DELETE(1);
	
	public final Integer value;
}
