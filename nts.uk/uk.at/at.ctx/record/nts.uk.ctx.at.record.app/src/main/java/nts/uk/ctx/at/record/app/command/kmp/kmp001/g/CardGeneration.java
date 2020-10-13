package nts.uk.ctx.at.record.app.command.kmp.kmp001.g;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardGeneration {

	// 社員コード
	private String employeeCd;

	// 生成した打刻カード
	private String cardNumber;

	// 重複する打刻カード
	private String duplicateCards;

}
