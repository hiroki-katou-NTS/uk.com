package nts.uk.ctx.pereg.dom.person.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 行単位のエラー警告情報
 * @author lanlt
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorWarningInfoOfRowOrder {
	//項目名
	private String itemName;
	//区分 - ErrorType
	private int errorType;
	//行番号
	private int rowOrder;
	//メッセージ
	private String message;

	

}
