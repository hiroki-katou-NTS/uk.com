package nts.uk.ctx.exio.dom.exi.extcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 特殊区分項目の編集の返す値
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialEditValue {
	/**
	 * 値 (変換後)
	 */
	private Object editValue;
	/**
	 * エラー：　True　エラー、False　エラーなし
	 */
	private boolean chkError;
	/**
	 * エラーメッセージ
	 */
	private String errorContent;
	/**
	 * 社員ID
	 */
	private String sid;
}
