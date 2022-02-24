package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetExtractedEmployeeCardNoInput {
	
	// 契約コード
	public String contractCode;
	
	// 社員ID
	public List<String> sIds; 
	
	// サイズQRCODE
	public int qrSize;
	
	// 縦
	public int setRow;
	
	// 横
	public int setCol;
	
}
