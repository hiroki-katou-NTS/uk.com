package nts.uk.screen.at.app.query.kmp.kmp001.e;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardNumbersMassGeneratedDto {
	// 社員コード
	private String employeeCd;

	// 生成した打刻カード
	private String cardNumber;

	// 重複する打刻カード
	private String duplicateCard;
}