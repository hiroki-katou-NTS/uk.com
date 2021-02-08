package nts.uk.ctx.exio.dom.exi.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AcceptItemEditValueDto {

	private Object editValue;
	
	/**
	 * チェック結果：False：エラー、True：エラーなし
	 */
	private boolean resultCheck;
	/**
	 * チェックのエラー内容
	 */
	private String editError;

}
