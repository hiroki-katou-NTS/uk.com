package nts.uk.ctx.link.smile.app.smilelink;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author yen_nth
 *
 */
@Data
@NoArgsConstructor
public class LinkedPaymentDto {
	// 支払コード
		private int paymentCode;
		
		// 選択雇用コード
		private List<EmploymentLinkedMonthDto> selectiveEmploymentCodes;

		public LinkedPaymentDto(int paymentCode, List<EmploymentLinkedMonthDto> selectiveEmploymentCodes) {
			super();
			this.paymentCode = paymentCode;
			this.selectiveEmploymentCodes = selectiveEmploymentCodes;
		}
}
