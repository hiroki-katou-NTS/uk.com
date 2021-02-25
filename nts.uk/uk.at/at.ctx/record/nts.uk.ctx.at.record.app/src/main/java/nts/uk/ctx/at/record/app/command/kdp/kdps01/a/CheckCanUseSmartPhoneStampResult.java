package nts.uk.ctx.at.record.app.command.kdp.kdps01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckCanUseSmartPhoneStampResult {

	// 打刻カード番号
	private String cardNumber;

	// 打刻利用可否
	private int used;
}
