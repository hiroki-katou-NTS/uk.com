package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thanhnx
 * 本人確認処理の利用設定
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityProcessUseSetDto {
	/**
	 * 日の本人確認を利用する
	 */
	private boolean useConfirmByYourself;
	/**
	 * 月の本人確認を利用する
	 */
	private boolean useIdentityOfMonth;
	
	/**
	 * エラーがある場合の日の本人確認
	 */
	private Integer yourSelfConfirmError;
}
