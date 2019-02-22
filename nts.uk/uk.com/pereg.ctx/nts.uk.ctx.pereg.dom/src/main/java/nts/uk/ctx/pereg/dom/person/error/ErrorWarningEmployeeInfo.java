package nts.uk.ctx.pereg.dom.person.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 社員のエラー警告情報
 * @author lanlt
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorWarningEmployeeInfo {
	//対象者
	private String employeeId;
	//対象者社員CD
	private String empCd;
	//対象者氏名
	private String empName;	
	//行番号
	private int no;
	//登録結果
	private boolean isDisplayRegister;
	//区分 - ErrorType
	private int errorType;
	//項目名
	private String itemName;
	//メッセージ
	private String message;

}
