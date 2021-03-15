package nts.uk.screen.at.app.command.kdp.kdp003.a;

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

	/**
	 * 処理日
	 */
	public GeneralDate processDate;
	/**
	 * 実施内容
	 */
	public int executionContent;
	/**
	 * リソースID
	 */
	public String resourceID;
	/**
	 * エラーメッセージ
	 */
	public String messageError;
	
}
