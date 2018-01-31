package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyRecOpeFuncDto {
	
	/**
	 * 本人確認を利用する
	 */
	private BigDecimal useConfirmByYourself;

	/**
	 * エラーがある場合の本人確認
	 */
	private BigDecimal yourselfConfirmError;
}
