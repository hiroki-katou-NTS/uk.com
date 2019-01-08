package nts.uk.ctx.pereg.dom.person.error;

import java.util.List;

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
	private String employeeCd;
	//対象者氏名
	private String employeeName;	
	//エラー情報一覧
	private List<ErrorWarningInfoOfRowOrder> errorLst;

}
