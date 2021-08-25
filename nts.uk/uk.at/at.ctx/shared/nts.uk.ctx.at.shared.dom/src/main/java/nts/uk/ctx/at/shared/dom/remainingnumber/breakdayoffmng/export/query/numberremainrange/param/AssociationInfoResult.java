package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 * 
 *         紐付け情報の整合チェック結果
 */
@AllArgsConstructor
public enum AssociationInfoResult {

	NO_CORRECT(0, "補正の必要なし"),

	REDUCE_USE(1, "使用数を0.5日減らす"),

	DELETE(2, "削除する");

	public final int value;

	public final String name;

}
