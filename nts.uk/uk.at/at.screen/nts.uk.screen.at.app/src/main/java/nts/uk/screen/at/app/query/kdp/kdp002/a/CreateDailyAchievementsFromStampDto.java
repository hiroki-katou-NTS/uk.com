package nts.uk.screen.at.app.query.kdp.kdp002.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateDailyAchievementsFromStampDto {
	
	private List<MessageError> messageError;
}


@AllArgsConstructor
@NoArgsConstructor
@Data
class MessageError {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 社員ID
	 */
	private String employeeID;
	/**
	 * 処理日
	 */
	private GeneralDate processDate;
	/**
	 * 実施内容
	 */
	private int executionContent;
	/**
	 * リソースID
	 */
	private String resourceID;
	/**
	 * エラーメッセージ
	 */
	private String messageError;
}
