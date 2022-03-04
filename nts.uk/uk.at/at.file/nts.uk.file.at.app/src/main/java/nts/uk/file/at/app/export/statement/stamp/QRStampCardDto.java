package nts.uk.file.at.app.export.statement.stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tutt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QRStampCardDto {
	
	// 社員ID
	private String SID;
	
	// カード番号
	private String cardNumber;
	
	// 社員コード
	private String employeeCode;
	
	//　社員名前
	private String employeeName;

}