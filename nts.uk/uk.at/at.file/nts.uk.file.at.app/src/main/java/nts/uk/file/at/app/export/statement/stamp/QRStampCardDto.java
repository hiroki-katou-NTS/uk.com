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
	private String SID;
	private String cardNumber;
	private String employeeCode;
	private String employeeName;

}