package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyRecOpeFuncDto {
	
	/**
	 * 本人確認を利用する
	 */
	private int useConfirmByYourself;

	/**
	 * エラーがある場合の本人確認
	 */
	private int yourselfConfirmError;
	
	/**
	 * 上司確認を利用する
	 */
	private int useSupervisorConfirm;

	/**
	 * エラーがある場合の上司確認
	 */
	private int supervisorConfirmError;
}
